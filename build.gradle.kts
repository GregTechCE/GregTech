import com.google.gson.JsonObject
import com.matthewprenger.cursegradle.CurseExtension
import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.CurseRelation
import net.minecraftforge.gradle.user.UserBaseExtension
import org.apache.commons.lang3.StringUtils
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevObject
import org.eclipse.jgit.revwalk.RevTag
import org.eclipse.jgit.revwalk.RevWalk
import java.util.*

buildscript {
    repositories {
        mavenCentral()
        maven {
            name = "jitpack"
            setUrl("https://jitpack.io")
        }
        maven {
            name = "forge"
            setUrl("https://maven.minecraftforge.net/")
        }
    }
    dependencies {
        classpath("com.github.GregTechCE:ForgeGradle:FG_2.3-SNAPSHOT")
        classpath("org.eclipse.jgit:org.eclipse.jgit:5.8.0.202006091008-r")
        classpath("org.apache.commons:commons-lang3:3.12.0")
    }
}

plugins {
    id("com.matthewprenger.cursegradle") version "1.1.0"
    id("maven-publish")
}

apply {
    plugin("net.minecraftforge.gradle.forge")
}

val config: Properties = file("build.properties").inputStream().let {
    val prop = Properties()
    prop.load(it)
    return@let prop
}

val mcVersion = config["minecraft.version"] as String
val mcFullVersion = "$mcVersion-${config["forge.version"]}"
val shortVersion = mcVersion.substring(0, mcVersion.lastIndexOf("."))
val strippedVersion = shortVersion.replace(".", "") + "0"

val forestryVersion = config["forestry.version"] as String
val chickenasmVersion = config["chickenasm.version"] as String
val cclVersion = config["ccl.version"] as String
val crafttweakerVersion = config["crafttweaker.version"] as String
val jeiVersion = config["jei.version"] as String
val topVersion = config["top.version"] as String
val ctmVersion = config["ctm.version"] as String

val git: Git = Git.open(projectDir)

val modVersion = getVersionFromJava(file("src/main/java/gregtech/GregTechVersion.java"))
val modVersionNoBuild = modVersion.substring(0, modVersion.lastIndexOf('.'))
version = "$mcVersion-$modVersion"
group = "gregtech"

configure<BasePluginConvention> {
    archivesBaseName = "gregtech"
}

fun minecraft(configure: UserBaseExtension.() -> Unit) = project.configure(configure)

minecraft {
    version = mcFullVersion
    mappings = "stable_39"
    runDir = "run"
    isUseDepAts = true
}

repositories {
    maven {
        name = "ic2, forestry"
        setUrl("http://maven.ic2.player.to/")
    }
    maven { //JEI
        name = "Progwml6 maven"
        setUrl("http://dvs1.progwml6.com/files/maven/")
    }
    maven { //JEI fallback
        name = "ModMaven"
        setUrl("modmaven.k-4u.nl")
    }
    maven {
        name = "tterrag maven"
        setUrl("http://maven.tterrag.com/")
    }
    maven {
        name = "ChickenBones maven"
        setUrl("http://chickenbones.net/maven/")
    }
    maven {
        name = "CoFH Maven"
        setUrl("http://maven.covers1624.net")
    }
    maven {
        name = "CraftTweaker Maven"
        setUrl("https://maven.blamejared.com/")
    }
    maven {
        name = "CCL Maven New"
        setUrl("https://minecraft.curseforge.com/api/maven")
    }
}

dependencies {
    "deobfCompile"("net.sengir.forestry:forestry_$mcVersion:$forestryVersion") {
        isTransitive = false
    }
    "deobfCompile"("codechicken:ChickenASM:$shortVersion-$chickenasmVersion")
    "deobfCompile"("codechicken-lib-1-8:CodeChickenLib-$mcVersion:$cclVersion:universal")
    "deobfCompile"("CraftTweaker2:CraftTweaker2-MC$strippedVersion-Main:$crafttweakerVersion")
    "deobfCompile"("mezz.jei:jei_$mcVersion:$jeiVersion")
    "deobfCompile"("mcjty.theoneprobe:TheOneProbe-$shortVersion:$shortVersion-$topVersion")
    "deobfCompile"("team.chisel.ctm:CTM:MC$mcVersion-$ctmVersion")

    "testImplementation"("junit:junit:4.13.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val processResources: ProcessResources by tasks
val sourceSets: SourceSetContainer = the<JavaPluginConvention>().sourceSets

processResources.apply {
    inputs.property("version", modVersion)
    inputs.property("mcversion", mcFullVersion)

    from(sourceSets["main"].resources.srcDirs) {
        include("mcmod.info")
        expand(mapOf("version" to modVersion,
            "mcversion" to mcFullVersion))
    }

    // copy everything else, thats not the mcmod.info
    from(sourceSets["main"].resources.srcDirs) {
        exclude("mcmod.info")
    }
    // access transformer
    rename("(.+_at.cfg)", "META-INF/$1")
}

val jar: Jar by tasks
jar.apply {
    manifest {
        attributes(mapOf("FMLAT" to "gregtech_at.cfg",
            "FMLCorePlugin" to "gregtech.common.asm.GTCELoadingPlugin",
            "FMLCorePluginContainsFMLMod" to "true"))
    }
}

val sourceTask: Jar = tasks.create("source", Jar::class.java) {
    from(sourceSets["main"].allSource)
    classifier = "sources"
}

val devTask: Jar = tasks.create("dev", Jar::class.java) {
    from(sourceSets["main"].output)
    classifier = "dev"
}

val energyApiTask: Jar = tasks.create("energyApi", Jar::class.java) {
    from(sourceSets["main"].allSource)
    from(sourceSets["main"].output)

    include("gregtech/api/capability/IElectricItem.*")
    include("gregtech/api/capability/IEnergyContainer.*")
    include("gregtech/api/capability/GregtechCapabilities.*")

    classifier = "energy-api"
}

artifacts {
    add("archives", jar)
    add("archives", sourceTask)
    add("archives", energyApiTask)
}

fun Project.idea(configure: org.gradle.plugins.ide.idea.model.IdeaModel.() -> Unit): Unit =
    (this as ExtensionAware).extensions.configure("idea", configure)
idea {
    module {
        inheritOutputDirs = true
    }
}

tasks.create("ciWriteBuildNumber") {
    doLast {
        val file = file("src/main/java/gregtech/GregTechVersion.java")
        val bn = getBuildNumber()
        val ln = "\n" //Linux line endings because we"re on git!

        var outfile = ""

        println("Build number: $bn")

        file.forEachLine { s ->
            var out = s
            if (out.matches(Regex("^ {4}public static final int BUILD = [\\d]+;\$"))) {
                out = "    public static final int BUILD = $bn;"
            }
            outfile += (out + ln)
        }
        file.writeText(outfile)
    }
}

tasks.create("generateChangelog") {
    doLast {
        val file = file("CHANGELOG.md")
        val fileContents = StringBuilder(file.readText(Charsets.UTF_8))
        val versionHeader = "\n### $modVersionNoBuild\n"
        if (fileContents.contains(versionHeader)) return@doLast
        val firstNewline = fileContents.indexOf('\n')
        val changelog = getActualChangeList()
        val insertText = "\n$versionHeader$changelog"
        fileContents.insert(firstNewline, insertText)
        file.writeText(fileContents.toString(), Charsets.UTF_8)
    }
}

val curseforgeProject = configureCurseforgeTask()
//just because curseforge task is retarded
curseforgeProject?.uploadTask?.outputs?.upToDateWhen { false }

fun resolveGitLabDownloadLink(): String? {
    val gitlabJobId: String = System.getenv("CI_JOB_ID") ?: return null
    return "https://gitlab.com/GregTechCE/GregTech/-/jobs/$gitlabJobId/artifacts/raw/build/libs/${jar.archiveName}"
}

fun resolveCurseforgeArtifactId(): String? {
    if (curseforgeProject == null) {
        return null
    }
    val artifact = curseforgeProject.additionalArtifacts.first()
    return artifact.parentFileID?.toString()
}

fun resolveCurseForgeDownloadLink(): String? {
    val cfArtifactId = resolveCurseforgeArtifactId() ?: return null
    return "https://www.curseforge.com/minecraft/mc-mods/gregtechce/files/$cfArtifactId"
}

fun resolveVersionChangelog(): String {
    val changeLogLines = file("CHANGELOG.md").readLines(Charsets.UTF_8)
    val versionHeader = "### $modVersionNoBuild"
    val startLineIndex = changeLogLines.indexOf(versionHeader)
    if (startLineIndex == -1) {
        return "No changelog provided"
    }
    val changelogBuilder = StringBuilder()
    var lineIndex = startLineIndex
    while (lineIndex < changeLogLines.size) {
        val changelogLine = changeLogLines[lineIndex]
        if (changelogLine.isEmpty()) break
        changelogBuilder.append(changelogLine).append("\n")
        lineIndex++
    }
    return changelogBuilder.toString()
}

val notificationTask: Task = tasks.create("postDiscordNotification") {
    doLast {
        val webhookId = System.getenv("DISCORD_WEBHOOK_ID") ?: error("Discord webhook id not set")
        val webhookToken = System.getenv("DISCORD_WEBHOOK_TOKEN") ?: error("Discord webhook token not set")
        val curseForgeDownloadLink = resolveCurseForgeDownloadLink()
        val gitLabDownloadLink = resolveGitLabDownloadLink()
        val message = StringBuilder("New GTCE version $modVersionNoBuild is out!\n")
        if (gitLabDownloadLink != null) {
            message.append("GitLab download:\n")
            message.append(gitLabDownloadLink)
            message.append("\n")
        }
        if (curseForgeDownloadLink != null) {
            message.append("CurseForge download (may take a while to be approved):\n")
            message.append(curseForgeDownloadLink)
            message.append("\n")
        }
        message.append("```${resolveVersionChangelog()}```")

        val jsonObject = JsonObject()
        jsonObject.addProperty("content", message.toString())

        val httpClient = HttpClientBuilder.create().build()
        val webhookUrl = "https://discordapp.com/api/webhooks/$webhookId/$webhookToken"
        val response = httpClient.execute(HttpPost(webhookUrl).also {
            it.entity = StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON)
        })
        if (response.statusLine.statusCode !in 200..210) {
            val status = response.statusLine
            throw IllegalStateException("Failed to send discord notification - ${status.statusCode} - ${status.reasonPhrase}")
        }
    }
}

if (curseforgeProject != null) {
    notificationTask.dependsOn("curseforge")
}

tasks["build"].dependsOn("generateChangelog")
tasks["ciWriteBuildNumber"].dependsOn("generateChangelog")
notificationTask.dependsOn("generateChangelog")

fun getPrettyCommitDescription(commit: RevCommit): String {
    val closePattern = Regex("(Closes|Fixes) #[0-9]*\\.?")
    val author = commit.authorIdent.name
    val message = commit.fullMessage
        //need to remove messages that close issues from changelog
        .replace(closePattern, "")
        //cut multiple newlines, format them all to linux line endings
        .replace(Regex("(\r?\n){1,}"), "\n")
        //cut squashed commit sub-commits descriptions
        .replace(Regex("\\* [^\\n]*\\n"), "")
        //split commit message on lines, trim each one
        .split('\n').asSequence().map { it.trim() }
        .filterNot { it.isBlank() }
        //cut out lines that are related to merges
        .filter { !it.startsWith("Merge remote-tracking branch") }
        //cut lines that carry too little information
        .filter { it.length > 3 }
        //captialize each line, add . at the end if it's not there
        .map { StringUtils.capitalize(it) }
        .map { if (it.endsWith('.')) it.substring(0, it.length - 1) else it }
        //append author to each line
        .map { "* $it - $author" }.toList()
    return message.joinToString( separator = "\n")
}

fun getCommitFromTag(revWalk: RevWalk, tagObject: RevObject) : RevCommit? {
    return when (tagObject) {
        is RevCommit -> tagObject
        is RevTag -> getCommitFromTag(revWalk, revWalk.parseAny(tagObject.`object`))
        else -> error("Encountered version tag pointing to a non-commit object: $tagObject")
    }
}

fun getActualTagName(tagName: String): String {
    val latestSlash = tagName.lastIndexOf('/')
    return tagName.substring(latestSlash + 1)
}

fun getActualChangeList(): String {
    val revWalk = RevWalk(git.repository)
    val latestTagCommit = git.tagList().call()
        .filter { getActualTagName(it.name).startsWith("v") }
        .mapNotNull { getCommitFromTag(revWalk, revWalk.parseAny(it.objectId)) }
        .maxBy { it.commitTime } ?: error("No previous release version tag found")

    val gitLog = git.log()
    val headCommitId = git.repository.resolve(Constants.HEAD)
    gitLog.addRange(latestTagCommit, headCommitId)
    val commitsBetween = gitLog.call().map { getPrettyCommitDescription(it) }.filterNot { it.isBlank() }
    return commitsBetween.joinToString(separator = "\n")
}

fun getBuildNumber(): String {
    val gitLog = git.log()
    val headCommitId = git.repository.resolve(Constants.HEAD)
    val startCommitId = ObjectId.fromString("c795901d796fba8ce8d3cb87d0172c59f56f3c9b")
    gitLog.addRange(startCommitId, headCommitId)
    return gitLog.call().toList().size.toString()
}

fun getVersionFromJava(file: File): String  {
    var major = "0"
    var minor = "0"
    var revision = "0"

    val prefix = "public static final int"
    file.forEachLine { line ->
        var s = line.trim()
        if (s.startsWith(prefix)) {
            s = s.substring(prefix.length, s.length - 1)
            s = s.replace("=", " ").replace(" +", " ").trim()
            val pts = s.split(" ")

            when {
                pts[0] == "MAJOR" -> major = pts[pts.size - 1]
                pts[0] == "MINOR" -> minor = pts[pts.size - 1]
                pts[0] == "REVISION" -> revision = pts[pts.size - 1]
            }
        }
    }

    val branchNameOrTag = System.getenv("CI_COMMIT_REF_NAME")
    if (branchNameOrTag != null && !branchNameOrTag.startsWith("v") && branchNameOrTag != "master") {
        return "$major.$minor.$revision-$branchNameOrTag"
    }

    val build = getBuildNumber()

    return "$major.$minor.$revision.$build"
}

fun CurseExtension.project(config: CurseProject.() -> Unit) = CurseProject().also {
    it.config()
    curseProjects.add(it)
}


// has to be called after addArtifact ¯\_(ツ)_/¯
fun CurseProject.relations(config: CurseRelation.() -> Unit) = CurseRelation().also {

    it.config()

    additionalArtifacts.forEach { artifact ->
        artifact.curseRelations = it
    }

    mainArtifact.curseRelations = it
}

fun configureCurseforgeTask(): CurseProject? {
    if (System.getenv("CURSE_API_KEY") != null) {
        val extension = curseforge
        extension.apiKey = System.getenv("CURSE_API_KEY")
        return extension.project {
            apiKey = System.getenv("CURSE_API_KEY")
            id = "293327"
            changelog = file("CHANGELOG.md")
            changelogType = "markdown"
            releaseType = "release"

            mainArtifact(jar)
            addArtifact(sourceTask)
            addArtifact(energyApiTask)

            relations {
                requiredDependency("codechicken-lib-1-8")
                optionalDependency("crafttweaker")
                optionalDependency("jei")
                optionalDependency("the-one-probe")
                optionalDependency("ctm")
            }
        }
    } else {
        println("Skipping curseforge task as there is no api key in the environment")
        return null
    }
}

publishing {
    publications {
        create("GTCEPublication", MavenPublication::class.java) {
            groupId = project.group as String
            artifactId = the<BasePluginConvention>().archivesBaseName
            version = project.version as String

            artifact(jar)
            artifact(sourceTask)
            artifact(energyApiTask)
        }
    }
}
