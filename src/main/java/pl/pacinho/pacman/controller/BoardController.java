package pl.pacinho.pacman.controller;

import pl.pacinho.pacman.logic.Levels;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.PlayerDirection;
import pl.pacinho.pacman.view.Board;
import pl.pacinho.pacman.view.cells.Cell;
import pl.pacinho.pacman.view.cells.EmptyCell;
import pl.pacinho.pacman.view.cells.PlayerCell;
import pl.pacinho.pacman.view.cells.WallCell;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BoardController {

    private Board board;
    private PlayerCell playerCell;

    private ArrayList<Integer> wallList;
    private boolean end = false;

    private JPanel boardPanel;

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
                boardPanel.add(cellInstance);
                if (cellInstance instanceof PlayerCell) {
                    playerCell = (PlayerCell) cellInstance;
                    playerCell.setPosition(idx);
                } else if (cellInstance instanceof WallCell) {
                    wallList.add(idx);
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
            System.out.println("Tick");
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
            refresh();
        }
    }

    private void replaceCells(int nextPosition) {
        if (wallList.contains(nextPosition)) {
            return;
        }

        boardPanel.remove(playerCell.getPosition());
        boardPanel.add(new EmptyCell(), playerCell.getPosition());

        boardPanel.remove(nextPosition);
        boardPanel.add(playerCell, nextPosition);
        playerCell.setPosition(nextPosition);
    }

    private void refresh() {
        board.repaint();
        board.revalidate();
        board.validate();
    }

}