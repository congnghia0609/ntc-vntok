# ntc-vntok
ntc-vntok is a library Tokenizer for the Vietnamese language.  

## Quick start
```java
String s = "VNTok là công cụ tách từ Tiếng Việt.";
System.out.println(s);
VnTok vntok = new VnTok();
String rs = vntok.tokenizeSentence(s);
System.out.println(rs);
//VNTok là công_cụ tách từ Tiếng_Việt .
```


## License
This code is under the [Apache License v2](https://www.apache.org/licenses/LICENSE-2.0).  
