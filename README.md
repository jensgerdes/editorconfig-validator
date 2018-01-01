# *.editorconfig* Validator
[![Build Status](https://api.travis-ci.org/jensgerdes/editorconfig-validator.svg?branch=master)](https://travis-ci.org/jensgerdes/editorconfig-validator) [![Coverage Status](https://coveralls.io/repos/github/jensgerdes/editorconfig-validator/badge.svg?branch=master)](https://coveralls.io/github/jensgerdes/editorconfig-validator?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/eu.b1n4ry.editorconfig/editorconfig-validator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/eu.b1n4ry.editorconfig/editorconfig/) [![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/jensgerdes/editorconfig-validator/blob/master/LICENSE)

Java-Library to validate that a file complies with the rules defined in `.editorconfig`.
Please note, that this project is under heavy development and is currently **NOT READY FOR PRODUCTION**!


For information on EditorConfig in general, see [their website](http://editorconfig.org/).
 
* Checks for
  * Usage of correct Charset

 ## Supported styles 

* Supports configuration inheritance.
* Supported styles:
  * indent_size
  * indent_width
  * end_of_line
  * charset
  * trim_trailing_whitespace
  * insert_final_newline
  
* Unsupported config keys:
  * tab_width: This only defines the way code is being displayed, only useful for editors.
  

# License
* [MIT License](https://www.opensource.org/licenses/mit-license.php)
