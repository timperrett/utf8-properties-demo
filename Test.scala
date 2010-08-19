import java.io._
import java.nio.charset.Charset
import java.util.Properties

// Loads an ASCII-encoded file with an input stream
def loadAsciiProperties(file: String) = {
  val in = new FileInputStream(file)
  val props = new Properties
  props.load(in)
  in.close
  props
}

// Loads a non-ASCII file with a reader, providing the encoding
def loadProperties(file: String, charset: Charset) = {
  val in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))
  val props = new Properties
  props.load(in)
  in.close
  props
}

val UTF8 = Charset.forName("UTF-8")

// The expected values of the properties:
val alef = "\u05d0"
val shanghai = "\u4e0a\u6d77"

// Loading the ASCII version:
val props1 = loadAsciiProperties("ascii.properties")
assert(props1.getProperty("alef") == alef)
assert(props1.getProperty("shanghai") == shanghai)

// Loading the UTF-8 version:
val props2 = loadProperties("utf8.properties", UTF8)
assert(props2.getProperty("alef") == alef)
assert(props2.getProperty("shanghai") == shanghai)

// Loading the UTF-8 version with an initial BOM
val props3 = loadProperties("utf8_withBom.properties", UTF8)
// Failure! The initial BOM is not properly ignored, and therefore prepended to the name of the first property
println(props3.containsKey("alef")) // prints false
// The following properties are OK
assert(props3.getProperty("shanghai") == shanghai)

// Loading the UTF8 version with an initial BOM followed by a comment:
val props4 = loadProperties("utf8_withBomAndInitialComment.properties", UTF8)
// The initial BOM is not properly ignored, but this just results in the comment being interpreted as an additional, empty property
assert(props4.size == 3)
// Apart from having an extra property with a phony name, the other properties are OK:
assert(props4.getProperty("alef") == alef)
assert(props4.getProperty("shanghai") == shanghai)

