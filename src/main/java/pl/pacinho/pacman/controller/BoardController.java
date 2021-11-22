package pl.pacinho.pacman.controller;

import pl.pacinho.pacman.logic.Levels;
import pl.pacinho.pacman.model.CellType;
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

    public BoardController(Board board) {
        this.board = board;
        wallList = new ArrayList<>();
        boardPanel = board.getBoardPanel();
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
                }
                idx++;
            }
        }

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            board.getTimer().start();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerCell.setDirection(PlayerDirection.findByKey(e));
        }
    }

    public void gameTick() {
        if (!end) {
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
    }

    private void replaceCells(int nextPosition) {
        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];

        if (wallList.contains(nextPosition)) {
            return;
        }

        checkPoint(nextPosition);

        boardPanel.remove(playerCell.getPosition());
        boardPanel.add(new EmptyCell(), playerCell.getPosition());

        boardPanel.remove(nextPosition);
        boardPanel.add(playerCell, nextPosition);
        playerCell.setPosition(nextPosition);

        refresh();

        if(nextCell.getCellType()==CellType.FINISH){
            board.getTimer().stop();
            JOptionPane.showMessageDialog(board, "Level finish !");
        }
    }

    private void checkPoint(int nextPosition) {
        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];
        if (nextCell instanceof PointCell) {
            point++;
            System.out.println(point);
        }

        if (point == maxPoint
                && !finishCell) {
            setRandomFinishCell();
            finishCell=true;
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

    private void refresh() {
        board.repaint();
        board.revalidate();
        board.validate();
    }

}