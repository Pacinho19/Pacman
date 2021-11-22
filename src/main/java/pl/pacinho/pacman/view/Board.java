package pl.pacinho.pacman.view;

import javafx.scene.shape.Box;
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
    private int level;
    @Getter
    private Timer timer;
    private BoardController boardController;

    public Board(int level) {
        this.level = level;
        this.self = this;

        this.setTitle("Pacman");
        this.setSize(900, 900);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timer = new Timer(160, this);
        initLevelProperties();
        initComponents();
        initView();
        initActions();

        boardController = new BoardController(this);
        boardController.initLevelView();
    }


    private void initLevelProperties() {
        if(level>Levels.getLevelsMap().size()-1){
            JOptionPane.showMessageDialog(self, "No more levels ! Develop in progress..");
            System.exit(0);
        }
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

        lifePanel = new JPanel();
        lifePanel.setLayout(new BoxLayout(lifePanel, BoxLayout.X_AXIS));

        lifeJL = new JLabel("Life : ");
        lifeJL.setFont(new Font("Serif", Font.PLAIN, 30));

        pointsJL = new JLabel();
        pointsJL.setFont(new Font("Serif", Font.PLAIN, 20));
    }

    private void initView() {
        Container main = this.getContentPane();
        main.setLayout(new BorderLayout());

        main.add(boardPanel, BorderLayout.CENTER);
        main.add(restartJB, BorderLayout.SOUTH);

        topPanel.add(bonusTimeJL, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        leftPanel.add(lifeJL);
        leftPanel.add(lifePanel);
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(pointsJL, BorderLayout.EAST);
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

    @Getter
    private JPanel boardPanel;
    private JButton restartJB;
    private JPanel topPanel;
    @Getter
    private JPanel lifePanel;
    private JLabel lifeJL;
    @Getter
    private JLabel pointsJL;
    @Getter
    private JLabel bonusTimeJL;
    private Board self;
}