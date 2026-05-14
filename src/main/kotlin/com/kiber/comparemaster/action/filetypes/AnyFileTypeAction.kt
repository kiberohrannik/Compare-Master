package com.kiber.comparemaster.action.filetypes

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.wm.ToolWindow
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.content.file.EFileTypes

class AnyFileTypeAction(private val toolWindow: ToolWindow) : PluginAction(
    hint = "Use generic text format",
    description = "Type or choose a generic format for syntax highlighting",
    icon = AllIcons.FileTypes.Text
) {

    companion object {
        private var lastSelected: AnyTypeProfile = AnyTypeProfile.TEXT
    }

    private val suggestions = AnyTypeProfile.entries.map { it.displayName }.toTypedArray()

    override fun actionPerformed(event: AnActionEvent) {
        val dialog = AnyTypeSelectDialog(
            project = getProject(event),
            options = suggestions.toList(),
            initialValue = lastSelected.displayName
        )

        if (!dialog.showAndGet()) {
            return
        }

        val selected = dialog.selectedValue()

        val profile = resolveProfile(selected)
        if (profile == null) {
            return
        }

        lastSelected = profile
        FileTypeSwitcher.apply(
            toolWindow = toolWindow,
            newType = EFileTypes.ANY,
            project = getProject(event),
            anyFileType = profile.resolveFileType()
        )
    }

    private fun resolveProfile(input: String): AnyTypeProfile? {
        val normalized = input.trim().lowercase()
        return AnyTypeProfile.entries.firstOrNull { profile ->
            profile.aliases.any { it.equals(normalized, ignoreCase = true) }
        }
    }

    private enum class AnyTypeProfile(val displayName: String, val aliases: Set<String>, val extension: String?) {
        TEXT("Text", setOf("text", "plain text", "plain", "txt"), null),
        YAML("YAML", setOf("yaml", "yml"), "yml"),
        TOML("TOML", setOf("toml"), "toml"),
        MARKDOWN("Markdown", setOf("markdown", "md"), "md"),
        PROPERTIES("Properties", setOf("properties", "property"), "properties"),
        INI("INI", setOf("ini"), "ini"),
        CSV("CSV", setOf("csv"), "csv"),
        HTML("HTML", setOf("html", "htm"), "html"),
        SQL("SQL", setOf("sql"), "sql"),
        JAVASCRIPT("JavaScript", setOf("javascript", "js"), "js"),
        TYPESCRIPT("TypeScript", setOf("typescript", "ts"), "ts");

        fun resolveFileType(): FileType {
            if (extension == null) {
                return PlainTextFileType.INSTANCE
            }

            val fileType = FileTypeManager.getInstance().getFileTypeByExtension(extension)
            return if (fileType == PlainTextFileType.INSTANCE) PlainTextFileType.INSTANCE else fileType
        }
    }
}
