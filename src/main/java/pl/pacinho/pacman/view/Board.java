package pl.pacinho.pacman.view;

import javafx.util.Pair;
import lombok.Getter;
import pl.pacinho.pacman.controller.BoardController;
import pl.pacinho.pacman.logic.Levels;
import pl.pacinho.pacman.model.Direction;
import pl.pacinho.pacman.view.cells.Cell;
import pl.pacinho.pacman.view.cells.EmptyCell;
import pl.pacinho.pacman.view.cells.WallCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JFrame implements ActionListener {

    @Getter
    private int rows;
    @Getter
    private int cols;

    @Getter
    private String levelMap;

    @Getter
    private JPanel boardPanel;
    private int level;

    @Getter
    private Timer timer;
    private Board self;

    private boolean end = false;

    private BoardController boardController;

    public Board(int level) {

        this.level = level;
        this.self = this;

        this.setTitle("Pacman");
        this.setSize(900, 900);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timer = new Timer(200, this);
        initLevelProperties();
        initComponents();
        initView();
        initActions();

        boardController = new BoardController(this);
        boardController.initLevelView();
    }


    private void initLevelProperties() {
        levelMap = Levels.getLevelsMap().get(level);
        Pair<Integer, Integer> dimension = Levels.getDimension(levelMap);
        rows = dimension.getKey();
        cols = dimension.getValue();

    }

    private void initComponents() {
        boardPanel = new JPanel(new GridLayout(rows, cols));
    }

    private void initView() {
        Container main = this.getContentPane();
        main.setLayout(new BorderLayout());

        main.add(boardPanel, BorderLayout.CENTER);
    }

    private void initActions() {
        self.setFocusable(true);
        self.requestFocusInWindow();
        self.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boardController.keyPressed(e);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boardController.gameTick();
    }
}