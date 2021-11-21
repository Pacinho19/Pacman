package pl.pacinho.pacman.view;

import javafx.util.Pair;
import pl.pacinho.pacman.logic.Levels;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.Direction;
import pl.pacinho.pacman.view.cells.Cell;
import pl.pacinho.pacman.view.cells.EmptyCell;
import pl.pacinho.pacman.view.cells.PlayerCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JFrame implements ActionListener {

    private int rows;
    private int cols;

    private String levelMap;
    private JPanel board;
    private int level;

    private Timer timer;
    private Board self;

    private boolean end = false;

    private PlayerCell playerCell;

    public Board(int level) {
        this.level = level;
        this.self = this;

        this.setTitle("Pacman");
        this.setSize(900, 900);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timer = new Timer(1000, this);
        initLevelProperties();
        initComponents();
        initView();
        initActions();

        initLevelView();
    }

    private void initLevelView() {
        String[] rows = levelMap.split("\n");

        int idx = 0;
        for (String row : rows) {
            String[] cells = row.split(" ");
            for (String cell : cells) {
                Cell cellInstance = Levels.getCellInstance(CellType.findBySymbol(cell));
                board.add(cellInstance);
                if (cellInstance instanceof PlayerCell) {
                    playerCell = (PlayerCell) cellInstance;
                    playerCell.setPosition(idx);
                }
                idx++;
            }
        }

    }

    private void initLevelProperties() {
        levelMap = Levels.getLevelsMap().get(level);
        Pair<Integer, Integer> dimension = Levels.getDimension(levelMap);
        rows = dimension.getKey();
        cols = dimension.getValue();

    }

    private void initComponents() {
        board = new JPanel(new GridLayout(rows, cols));
    }

    private void initView() {
        Container main = this.getContentPane();
        main.setLayout(new BorderLayout());

        main.add(board, BorderLayout.CENTER);
    }

    private void initActions() {
        self.setFocusable(true);
        self.requestFocusInWindow();
        self.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timer.start();
                }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    playerCell.setDirection(Direction.RIGHT);
                }
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!end) {
            System.out.println("Tick");
            Component[] components = board.getComponents();
            if (playerCell.getDirection() == Direction.NONE) {
                return;
            } else if (playerCell.getDirection() == Direction.RIGHT) {
                board.remove(playerCell.getPosition());
                board.add(new EmptyCell(), playerCell.getPosition());

                board.remove(playerCell.getPosition()+1);
                board.add(playerCell, playerCell.getPosition()+1);
                playerCell.setPosition(playerCell.getPosition()+1);

            }
            refresh();

        }
    }

    private void refresh() {
        self.repaint();
        self.revalidate();
        self.validate();
    }

}