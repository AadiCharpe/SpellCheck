import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Set;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        SpellCheckFrame frame = new SpellCheckFrame();
        frame.setVisible(true);
    }
}

class SpellCheckFrame extends JFrame {
    private Set<String> englishWords;
    public SpellCheckFrame() {
        try {
            englishWords = new HashSet<>(60000);
            BufferedReader in = new BufferedReader(new FileReader("english.txt"));
            String line;
            while ((line = in.readLine()) != null)
                englishWords.add(line.toLowerCase());
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Spell Check");
        setSize(600, 400);

        JTextArea text = new JTextArea();
        text.setFont(new Font("Sanserif", Font.PLAIN, 16));
        JTextArea mistakes = new JTextArea(6, 20);
        mistakes.setFont(new Font("Sanserif", Font.PLAIN, 14));
        mistakes.setEditable(false);
        JButton check = new JButton("Check Spelling");
        check.addActionListener(e -> {
            mistakes.setText("");
            boolean mistake = false;
            StringTokenizer tokenizer = new StringTokenizer(text.getText(), " \n\t.?!,\"/()[]{}<>|");
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                if(!(token.equals("a") || token.equals("i") || englishWords.contains(token))) {
                    mistakes.setForeground(Color.RED);
                    mistakes.append("Misspelled \"" + token + "\"\n");
                    mistake = true;
                }
            }
            if(!mistake) {
                mistakes.setForeground(Color.GREEN);
                mistakes.append("No Spelling Mistakes!");
            }
        });
        JPanel panel = new JPanel();
        panel.add(new JLabel("Spelling Mistakes:"));
        panel.add(new JScrollPane(mistakes));
        panel.add(check);
        add(panel, "South");
        add(new JScrollPane(text));
    }
}