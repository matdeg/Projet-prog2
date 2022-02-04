import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

object SwingExample extends App {

    val textArea = new JTextArea("Hello, Swing world")
    val scrollPane = new JScrollPane(textArea)

    val frame = new JFrame("Hello, Swing")
    frame.getContentPane.add(scrollPane, BorderLayout.CENTER)
    frame.setDefaultCloseOperation(0)
    frame.setSize(new Dimension(600, 400))
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)

}