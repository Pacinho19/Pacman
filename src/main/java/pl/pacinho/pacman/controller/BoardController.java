package pl.pacinho.pacman.controller;

import pl.pacinho.pacman.config.GameProperties;
import pl.pacinho.pacman.logic.Levels;
import pl.pacinho.pacman.logic.Point;
import pl.pacinho.pacman.model.*;
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

    private int originalPlayerPosition = 0;
    private boolean finishCell = false;
    private int finishCellIdx = 0;

    private List<Point> pointsMap;

    private int monsterCount;
    private List<MonsterCell> monsters;

    private Bonus bonus;
    private int firstBonusDelay = 20;
    private int gameTicks = 0;

    private int life;

    public BoardController(Board board) {
        this.board = board;
        this.life=board.getLife();

        boardPanel = board.getBoardPanel();

        wallList = new ArrayList<>();
        pointsMap = new ArrayList<>();
        monsters = new ArrayList<>();

        bonus = new Bonus();
        monsterCount = board.getLevelData().getMonsterCount();
        setLifeCells();
    }

    private void setLifeCells() {
        board.getLifePanel().removeAll();
        for (int i = 0; i < life; i++) {
            board.getLifePanel().add(new LifeCell());
        }
        refresh();
    }

    public void initLevelView() {
        String[] rows = board.getLevelData().getBoard().split("\n");

        int idx = 0;
        for (String row : rows) {
            String[] cells = row.split(" ");
            for (String cell : cells) {
                Cell cellInstance = Levels.getCellInstance(CellType.findBySymbol(cell));
                cellInstance.setIdx(idx);
                boardPanel.add(cellInstance);
                if (cellInstance instanceof PlayerCell) {
                    playerCell = (PlayerCell) cellInstance;
                    originalPlayerPosition = playerCell.getIdx();
                } else if (cellInstance instanceof WallCell) {
                    wallList.add(idx);
                } else if (cellInstance instanceof PointCell) {
                    maxPoint++;
                    pointsMap.add(new Point(idx));
                }
                idx++;
            }
        }

        for (int i = 0; i < monsterCount; i++) {
            locateMonster();
        }
        monsters.forEach(m -> m.setMonsterDirection(MonsterDirection.RIGHT));
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!board.getTimer().isRunning()) {
                board.getTimer().start();
                end = false;
            } else {
                board.getTimer().stop();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerCell.setDirection(PlayerDirection.findByKey(e));
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            restart(GameProperties.getMaxLife());
        }
    }

    public void gameTick() {
        checkBonus();

        if (!bonus.isInUse() || bonus.getBonusType() != BonusType.MONSTER_FREEZE) {
            moveMonsters();
            refresh();
        }

        if (end) {
            return;
        }

        if (playerCell.getDirection() == PlayerDirection.NONE) {
            return;
        } else if (playerCell.getDirection() == PlayerDirection.RIGHT) {
            int nextPosition = playerCell.getIdx() + 1;
            replaceCells(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.LEFT) {
            int nextPosition = playerCell.getIdx() - 1;
            replaceCells(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.DOWN) {
            int nextPosition = playerCell.getIdx() + board.getCols();
            replaceCells(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.UP) {
            int nextPosition = playerCell.getIdx() - board.getCols();
            replaceCells(nextPosition);
        }

        refresh();
        gameTicks++;
    }

    private void checkBonus() {
        if (gameTicks <= firstBonusDelay) {
            return;
        }
        if (!bonus.isActive()) {
            bonus.incrementTickWithoutBonus();
            if (bonus.getTickWithoutBonus() >= bonus.getBonusDelay()) {
                addBonus();
            }
        }

        if (bonus.isInUse()) {
            bonus.incrementBonusTick();
            setBonusLabelText();
            if (bonus.getBonusTick() >= bonus.getBonusTime()) {
                bonus.clear();
                board.getBonusTimeJL().setText("");
                board.getBonusTimeJL().setVisible(false);
                playerCell.setOriginalBorder();
            }
        }
    }

    private void setBonusLabelText() {
        board.getBonusTimeJL().setText("Current bonus : " + bonus.getBonusType().toString() + ", Left ticks : " + (bonus.getBonusTime() - bonus.getBonusTick()));
        board.getBonusTimeJL().setVisible(true);
        refresh();
    }

    private void addBonus() {
        BonusType bt = BonusType.findById(RandomUtils.getInt(0, 2));

        List<Cell> pointCell = Arrays.stream(boardPanel.getComponents())
                .map(c -> (Cell) c)
                .filter(c -> c.getCellType() == CellType.EMPTY)
                .collect(Collectors.toList());

        int extraPointIdx = RandomUtils.getInt(0, pointCell.size() - 1);

        Cell cell = (pointCell.get(extraPointIdx));

        Cell celToBoard = null;
        if (bt == BonusType.MONSTER_FREEZE) {
            celToBoard = new ExtraPointCell(cell.getIdx(), cell instanceof PointCell);
        } else if (bt == BonusType.MONSTER_KILL) {
            celToBoard = new MonsterKillerCell(cell.getIdx(), cell instanceof PointCell);
        }
        boardPanel.remove(cell.getIdx());
        boardPanel.add(celToBoard, cell.getIdx());

        bonus.setActive(true);
        bonus.setCell(celToBoard);
        bonus.setBonusType(bt);
        refresh();
    }

    private void moveMonsters() {
        MonsterCell monsterCell1 = null;
        for (MonsterCell monsterCell : monsters) {
            monsterCell1 = goMove(monsterCell);
            if (monsterCell1 != null) {
                break;
            }
            if (end) {
                return;
            }
        }
        if (monsterCell1 != null) {
            monsters.remove(monsterCell1);
        }
    }

    private MonsterCell goMove(MonsterCell monsterCell) {
        int nextPosition = 0;
        if (monsterCell.getMonsterDirection() == MonsterDirection.NONE) {
            return null;
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
            return null;
        }

        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];
        if (nextCell instanceof MonsterCell) {
            MonsterDirection direction = RandomUtils.getMonsterDirection();
            monsterCell.setMonsterDirection(direction);
            return null;
        }
        boardPanel.remove(monsterCell.getIdx());
        if (bonus.isActive() && monsterCell.getIdx() == bonus.getCell().getIdx()) {
            boardPanel.add(bonus.getCell(), monsterCell.getIdx());
        } else if (pointsMap.stream().map(Point::getIdx).collect(Collectors.toList()).contains(monsterCell.getIdx())) {
            PointCell pointCell = new PointCell();
            pointCell.setIdx(monsterCell.getIdx());
            boardPanel.add(pointCell, monsterCell.getIdx());
        } else if (monsterCell.getIdx() == finishCellIdx) {
            boardPanel.add(new FinishCell(), monsterCell.getIdx());
        } else {
            boardPanel.add(new EmptyCell(monsterCell.getIdx()), monsterCell.getIdx());
        }

        if (nextCell instanceof PlayerCell
                && (!bonus.isInUse()
                || (bonus.getBonusType() != BonusType.MONSTER_KILL))) {

            int position = playerCell.getIdx();
            boardPanel.remove(position);
            boardPanel.add(monsterCell, position);
            monsterCell.setIdx(position);
            refresh();

            board.getTimer().stop();
            checkLife();
            return null;
        } else if (bonus.isInUse() && bonus.getBonusType() == BonusType.MONSTER_KILL && nextCell instanceof PlayerCell) {
            boardPanel.remove(nextPosition);
            boardPanel.add(new EmptyCell(nextPosition), nextPosition);
            boardPanel.remove(playerCell.getIdx());
            boardPanel.add(playerCell, playerCell.getIdx());

            monsters.remove(monsterCell);
            return monsterCell;
        }

        boardPanel.remove(nextPosition);
        boardPanel.add(monsterCell, nextPosition);
        monsterCell.setIdx(nextPosition);

        return null;
    }

    private void checkLife() {
        life--;
        setLifeCells();

        if (life == 0) {
            JOptionPane.showMessageDialog(board, "Game Over2 !");
            end = true;
        } else {
            boardPanel.remove(originalPlayerPosition);
            playerCell = new PlayerCell();
            playerCell.setIdx(originalPlayerPosition);
            boardPanel.add(playerCell, originalPlayerPosition);
        }
    }

    private void replaceCells(int nextPosition) {
        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];

        if (wallList.contains(nextPosition)) {
            return;
        }

        checkPoint(nextPosition);

        boardPanel.remove(playerCell.getIdx());
        boardPanel.add(new EmptyCell(playerCell.getIdx()), playerCell.getIdx());

        if (nextCell instanceof MonsterCell
                && (!bonus.isInUse()
                || (bonus.getBonusType() != BonusType.MONSTER_KILL))) {
            boardPanel.remove(nextPosition);
            boardPanel.add(new MonsterCell(0), nextPosition);

            board.getTimer().stop();
            checkLife();
            return;
        } else if (bonus.isInUse() && bonus.getBonusType() == BonusType.MONSTER_KILL && nextCell instanceof MonsterCell) {
            boardPanel.remove(nextPosition);
            boardPanel.add(new EmptyCell(nextPosition), nextPosition);
            boardPanel.remove(playerCell.getIdx());
            boardPanel.add(playerCell, playerCell.getIdx());

            monsters.remove((MonsterCell) nextCell);
            return;
        }

        boardPanel.remove(nextPosition);
        boardPanel.add(playerCell, nextPosition);
        playerCell.setIdx(nextPosition);

        refresh();

        if (nextCell.getCellType() == CellType.FINISH) {
            board.getTimer().stop();
            playerCell.setDirection(PlayerDirection.NONE);
            JOptionPane.showMessageDialog(board, "Level finish !");
            board.dispose();
            new Board(board.getLevel() + 1, life).setVisible(true);
        }
    }

    private void checkPoint(int nextPosition) {
        Point point = pointsMap.stream()
                .filter(p -> p.getIdx() == nextPosition)
                .findFirst()
                .orElse(null);

        if (point != null) {
            this.point++;
            setPointInfo();
            pointsMap.remove(point);
        }

        if (this.point == maxPoint
                && !finishCell) {
            setRandomFinishCell();
            finishCell = true;
        }

        Cell nextCell = (Cell) boardPanel.getComponents()[nextPosition];
        if (nextCell instanceof ExtraPointCell || nextCell instanceof MonsterKillerCell) {
            bonus.setInUse(true);
            if (nextCell instanceof MonsterKillerCell) {
                playerCell.setMonsterKillBonusBorder();
            }
        }
    }

    private void setPointInfo() {
        board.getPointsJL().setText("Points : " + point + "/" + maxPoint);
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

        this.finishCellIdx = cell.getIdx();
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

    public void restart(int life) {
        board.dispose();
        new Board(0, life).setVisible(true);
    }

    private void refresh() {
        board.repaint();
        board.revalidate();
        board.validate();
    }

}