Illustrates Java 6's ability to load properties files with any encoding.

The test input is a properties file defining two entries:

* the hebrew letter [alef](http://www.fileformat.info/info/unicode/char/05d0/index.htm) (U+05d0);
* the word "shanghai" in chinese, which is composed of the Unicode characters [U+4e0a](http://www.fileformat.info/info/unicode/char/4e0a/index.htm) and [U+6d77](http://www.fileformat.info/info/unicode/char/6d77/index.htm).

`ascii.properties` is the "legacy" format that worked before Java 6: ASCII-encoded, with non-ASCII Unicode code points escaped.

`utf8.properties` is the same file, but encoded in UTF-8 and with the actual Unicode characters.

Additionally, I've found out that an Unicode stream _may_ start with a "byte order mark", that should be ignored. From the [Unicode FAQ](http://www.unicode.org/faq/utf_bom.html#BOM):

> A byte order mark (BOM) consists of the character code U+FEFF at the beginning of a data stream, where it can be used as a signature defining the byte order and encoding form, primarily of unmarked plaintext files.
> [...]
> [concerning UTF-8] An initial BOM is only used as a signature — an indication that an otherwise unmarked text file is in UTF-8.

And there is actually a bug in Java's UTF-8 implementation: the BOM is not properly ignored (see [this blog post](http://tripoverit.blogspot.com/2007/04/javas-utf-8-and-unicode-writing-is.html) and [the entry in Java's bug database](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4508058)).

So there will be a problem if an UTF-8 encoded properties file starts with a BOM, but there is a simple workaround: start the file with a comment. I've added two more files to illustrate this: `utf8_withBom.properties` and `utf8_withBomAndInitialComment.properties`.

`Test.scala` loads the 4 versions and checks that the resulting values are correct.

