package des.c5inco.toolbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.ToolWindowManager
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel


class SummonToolAction: DumbAwareAction() {
    private lateinit var popup: JBPopup
    private var project: Project? = null

    override fun actionPerformed(e: AnActionEvent) {
        project = e.project

        project?.let {
            // Create a JPanel to use as the content of the JBPopup
            val content = composePanel(project)

            // Create a JBPopup instance using the content panel
            popup = JBPopupFactory.getInstance().createComponentPopupBuilder(content, null)
                .setResizable(true)
                .setMovable(true)
                .setFocusable(true)
                .createPopup()

            // Show the JBPopup in the best position for the current context
            popup.showInBestPositionFor(e.dataContext)
        }
    }

    fun composePanel(project: Project?) : JComponent {
        return ComposePanel().apply {
            preferredSize = Dimension(400, 200)
            setContent {
                MaterialTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column {
                            Button(onClick = { test("Bookmarks") }) {
                                Text("Bookmarks")
                            }
                            Button(onClick = { test("Project") }) {
                                Text("Project")
                            }
                        }
                    }
                }
            }
        }
    }

    fun test(window: String) {
        project?.let {
            ToolWindowManager.getInstance(it).getToolWindow(window)?.show(null)
            popup.setUiVisible(false)
        }
    }
}
