package pl.pacinho.pacman.view;

import javafx.util.Pair;
import lombok.Getter;
import pl.pacinho.pacman.controller.BoardController;
import pl.pacinho.pacman.logic.Levels;
import pl.pacinho.pacman.model.LevelData;

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
    private LevelData levelData;

    @Getter
    private JPanel boardPanel;
    private JButton restartJB;
    private JPanel topPanel;

    @Getter
    private JLabel bonusTimeJL;

    @Getter
    private int level;

    @Getter
    private Timer timer;

    private Board self;

    private BoardController boardController;

    public Board(int level) {
        this.level = level;
        this.self = this;

        this.setTitle("Pacman");
        this.setSize(900, 900);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timer = new Timer(180, this);
        initLevelProperties();
        initComponents();
        initView();
        initActions();

        boardController = new BoardController(this);
        boardController.initLevelView();
    }


    private void initLevelProperties() {
        levelData = Levels.getLevelsMap().get(level);
        Pair<Integer, Integer> dimension = Levels.getDimension(levelData.getBoard());
        rows = dimension.getKey();
        cols = dimension.getValue();
    }

    private void initComponents() {
        boardPanel = new JPanel(new GridLayout(rows, cols));
        boardPanel.setDoubleBuffered(true);

        restartJB = new JButton("Restart");

        topPanel = new JPanel(new BorderLayout());

        bonusTimeJL = new JLabel();
        bonusTimeJL.setFont(new Font("Serif", Font.PLAIN, 20));
        bonusTimeJL.setVisible(false);
    }

    private void initView() {
        Container main = this.getContentPane();
        main.setLayout(new BorderLayout());

        main.add(boardPanel, BorderLayout.CENTER);
        main.add(restartJB, BorderLayout.SOUTH);

        topPanel.add(bonusTimeJL, BorderLayout.CENTER);
        topPanel.add(new JLabel("Test"), BorderLayout.EAST);
        main.add(topPanel, BorderLayout.NORTH);
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
        restartJB.addActionListener((e -> {
            boardController.restart();
        }
        ));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boardController.gameTick();
    }
}