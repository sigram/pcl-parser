A parser for the Printer Command Language version 5.


PCL5 has been created by Hewlett-Packard for the use in their line of
raster and vector printers. Since then many other printer vendors started
using it in their products.

PCL is a documented format, but implementations often differ in
many subtle (and not so subtle) details of how the format is to be
interpreted, so that parsing PCL correctly is a challenge. For example,
even among printers coming from a single vendor there are substantial
differences in interpretation of the same commands between dot-matrix,
laser and ink-jet printers, and between specific models in each category.

The following reference materials were used to implement this parser:

http://h20000.www2.hp.com/bc/docs/support/SupportManual/bpl13210/bpl13210.pdf
http://h20000.www2.hp.com/bc/docs/support/SupportManual/bpl13211/bpl13211.pdf
http://h20000.www2.hp.com/bc/docs/support/SupportManual/bpl13208/bpl13208.pdf
http://h20000.www2.hp.com/bc/docs/support/SupportManual/bpl13207/bpl13207.pdf

This package provides a basic interpreter that can be easily extended or
modified to cover any particular variant of the language. The original
purpose of this parser was text extraction, so the handling of details of
formatting and graphics is less reliable.

PCL format allows for adding extensions, e.g. sections in other printer
languages. Some of the commonly used ones are PJL (Print Job Language)
and HPGL (HP Graphics Language). This package offers a limited support
for parsing these languages.
