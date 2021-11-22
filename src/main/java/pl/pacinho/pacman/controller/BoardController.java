package pl.pacinho.pacman.controller;

import pl.pacinho.pacman.logic.Levels;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.MonsterDirection;
import pl.pacinho.pacman.model.PlayerDirection;
import pl.pacinho.pacman.utils.RandomUtils;
import pl.pacinho.pacman.view.Board;
import pl.pacinho.pacman.view.cells.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoardController {

    private Board board;
    private PlayerCell playerCell;

    private ArrayList<Integer> wallList;
    private boolean end = false;

    private JPanel boardPanel;

    private int point = 0;
    private int maxPoint = 0;

    private boolean finishCell = false;

    private List<Cell> pointsMap;

    private int monsterCount = 2;

    private List<MonsterCell> monsters;

    public BoardController(Board board) {
        this.board = board;
        boardPanel = board.getBoardPanel();

        wallList = new ArrayList<>();
        pointsMap = new ArrayList<>();
        monsters = new ArrayList<>();
    }

    public void initLevelView() {
        String[] rows = board.getLevelMap().split("\n");

        int idx = 0;
        for (String row : rows) {
            String[] cells = row.split(" ");
            for (String cell : cells) {
                Cell cellInstance = Levels.getCellInstance(CellType.findBySymbol(cell));
                cellInstance.setIdx(idx);
                boardPanel.add(cellInstance);
                if (cellInstance instanceof PlayerCell) {
                    playerCell = (PlayerCell) cellInstance;
                    playerCell.setPosition(idx);
                } else if (cellInstance instanceof WallCell) {
                    wallList.add(idx);
                } else if (cellInstance instanceof PointCell) {
                    maxPoint++;
                    pointsMap.add(cellInstance);
                }
                idx++;
            }
        }

        for (int i = 0; i < monsterCount; i++) {
            locateMonster();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            board.getTimer().start();
            monsters.forEach(m -> m.setMonsterDirection(MonsterDirection.RIGHT));
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerCell.setDirection(PlayerDirection.findByKey(e));
        }
    }

    public void gameTick() {
        moveMonsters();
        refresh();

        if(end){
            return;
        }

        if (playerCell.getDirection() == PlayerDirection.NONE) {
            return;
        } else if (playerCell.getDirection() == PlayerDirection.RIGHT) {
            int nextPosition = playerCell.getPosition() + 1;
            replaceCells(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.LEFT) {
            int nextPosition = playerCell.getPosition() - 1;
            replaceCells(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.DOWN) {
            int nextPosition = playerCell.getPosition() + board.getCols();
            replaceCells(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.UP) {
            int nextPosition = playerCell.getPosition() - board.getCols();
            replaceCells(nextPosition);
        }
    }

    private void moveMonsters() {
        for (MonsterCell monsterCell : monsters) {
            goMove(monsterCell);
            if(end){
                return;
            }
        }
    }

    private void goMove(MonsterCell monsterCell) {
        int nextPosition = 0;
        if (monsterCell.getMonsterDirection() == MonsterDirection.NONE) {
            return;
        } else if (monsterCell.getMonsterDirection() == MonsterDirection.RIGHT) {
            nextPosition = monsterCell.getIdx() + 1;
        } else if (monsterCell.getMonsterDirection() == MonsterDirection.LEFT) {
            nextPosition = monsterCell.getIdx() - 1;
        } else if (monsterCell.getMonsterDirection() == MonsterDirection.DOWN) {
            nextPosition = monsterCell.getIdx() + board.getCols();
        } else if (monsterCell.getMonsterDirection() == MonsterDirection.UP) {
            nextPosition = monsterCell.getIdx() - board.getCols();
        }

        if (wallList.contains(nextPosition)) {
            MonsterDirection direction = RandomUtils.getMonsterDirection();
            monsterCell.setMonsterDirection(direction);
            return;
        }

        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];
        if (nextCell instanceof MonsterCell) {
            MonsterDirection direction = RandomUtils.getMonsterDirection();
            monsterCell.setMonsterDirection(direction);
            return;
        }
        boardPanel.remove(monsterCell.getIdx());
        if (pointsMap.stream().map(p -> p.getIdx()).collect(Collectors.toList()).contains(monsterCell.getIdx())) {
            PointCell pointCell = new PointCell();
            pointCell.setIdx(monsterCell.getIdx());
            boardPanel.add(pointCell, monsterCell.getIdx());
        } else {
            boardPanel.add(new EmptyCell(monsterCell.getIdx()), monsterCell.getIdx());
        }

        if (nextCell instanceof PlayerCell) {

            int position = playerCell.getPosition();
            boardPanel.remove(position);
            boardPanel.add(monsterCell, position);
            monsterCell.setIdx(position);
            refresh();

            board.getTimer().stop();
            JOptionPane.showMessageDialog(board, "Game Over2 !");
            end= true;

            return;
        }

        boardPanel.remove(nextPosition);
        boardPanel.add(monsterCell, nextPosition);
        monsterCell.setIdx(nextPosition);

    }

    private void replaceCells(int nextPosition) {
        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];

        if (wallList.contains(nextPosition)) {
            return;
        }

        checkPoint(nextPosition);

        boardPanel.remove(playerCell.getPosition());
        boardPanel.add(new EmptyCell(nextCell.getIdx()), playerCell.getPosition());

        if (nextCell instanceof MonsterCell) {
            boardPanel.remove(nextPosition);
            boardPanel.add(new MonsterCell(0), nextPosition);
            refresh();
            board.getTimer().stop();

            JOptionPane.showMessageDialog(board, "Game Over1 !");
            end= true;

            return;
        }

        boardPanel.remove(nextPosition);
        boardPanel.add(playerCell, nextPosition);
        playerCell.setPosition(nextPosition);

        refresh();

        if (nextCell.getCellType() == CellType.FINISH) {
            board.getTimer().stop();
            playerCell.setDirection(PlayerDirection.NONE);
            JOptionPane.showMessageDialog(board, "Level finish !");
        }
    }

    private void checkPoint(int nextPosition) {
        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];
        if (nextCell instanceof PointCell) {
            point++;
            Cell cell = pointsMap.stream()
                    .filter(pm -> pm.getIdx() == nextCell.getIdx())
                    .findFirst()
                    .get();
            pointsMap.remove(cell);

            if (point == maxPoint
                    && !finishCell) {
                setRandomFinishCell();
                finishCell = true;
            }

        }
    }

    private void setRandomFinishCell() {
        List<Cell> emptyCells = Arrays.stream(boardPanel.getComponents())
                .map(c -> (Cell) c)
                .filter(c -> c.getCellType() == CellType.EMPTY)
                .collect(Collectors.toList());

        int finishCellIdx = RandomUtils.getInt(0, emptyCells.size() - 1);
        Cell cell = emptyCells.get(finishCellIdx);

        boardPanel.remove(cell.getIdx());
        boardPanel.add(new FinishCell(), cell.getIdx());
    }

    private void locateMonster() {
        List<Cell> pointCells = Arrays.stream(boardPanel.getComponents())
                .map(c -> (Cell) c)
                .filter(c -> c.getCellType() == CellType.POINT)
                .collect(Collectors.toList());

        int finishCellIdx = RandomUtils.getInt(0, pointCells.size() - 1);
        Cell cell = pointCells.get(finishCellIdx);

        MonsterCell monsterCell = new MonsterCell(cell.getIdx());
        boardPanel.remove(cell.getIdx());
        boardPanel.add(monsterCell, cell.getIdx());
        monsters.add(monsterCell);
    }


    private void refresh() {
        board.repaint();
        board.revalidate();
        board.validate();
    }

}