import java.nio.file.Files
import java.nio.file.Path

fun readAllText(path: String): String {
    return Files.readString(Path.of(path))
}
