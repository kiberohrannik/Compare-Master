Short answer: yes for editor syntax support, but not exactly the same "module-only" way as JSON/XML.

Your current setup:
- [plugin.xml](/home/kiberohrannik/IdeaProjects/CompareMaster/src/main/resources/META-INF/plugin.xml) depends on `com.intellij.modules.platform` and `com.intellij.modules.json` only.
- File type enum has only JSON/XML in [EFileTypes.kt](/home/kiberohrannik/IdeaProjects/CompareMaster/src/main/kotlin/com/kiber/comparemaster/content/file/EFileTypes.kt).
- Top toolbar has only JSON/XML actions in [EditorsToolWindowFactory.kt](/home/kiberohrannik/IdeaProjects/CompareMaster/src/main/kotlin/com/kiber/comparemaster/EditorsToolWindowFactory.kt).

What changes for YAML:
1. Add YAML plugin dependency in `plugin.xml`:
- `<depends>org.jetbrains.plugins.yaml</depends>`
2. Add YAML bundled plugin dependency in Gradle (`build.gradle.kts`):
- `intellijPlatform { bundledPlugin("org.jetbrains.plugins.yaml") }`
3. Add `YAML` to `EFileTypes`, plus `YamlEVirtualFile`, `YamlFileTypeAction`, and include it in `EditorsToolWindowFactory`.
4. Update `DefaultEditorsFileManager` `when` branches to handle YAML type creation/switching.

Important caveat:
- “Syntax in editor” is easy.
- Your current transformation actions (`Sort/Format/Inline/Replace/Add absent`) are JSON/XML-specific; YAML needs either:
  - dedicated YAML formatter/transformer logic, or
  - convert YAML <-> JSON internally before operations.

Also, XML already has unsupported actions (`Replace/Add absent`) today, so YAML would likely start as syntax-only first.

Sources:
- JetBrains Plugin Dependencies (YAML plugin ID and `<depends>` rules): https://plugins.jetbrains.com/docs/intellij/plugin-dependencies.html
- IntelliJ Platform Gradle Plugin dependencies (`bundledPlugin(...)`): https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
- Plugin/module compatibility behavior: https://plugins.jetbrains.com/docs/intellij/plugin-compatibility.html
