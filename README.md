##Rate it!

Demo: http://rateerate.appspot.com/


##Getting Started using Eclipse

1) Get you own copy of project using: 
    
     git clone git://github.com/jbreitbart/rate-it.git
     or 
     git plugin for Eclipse

2) Open it in Eclipse

    you need a Google plugin installed in your Eclipse

3) Edit settings

    - open Constants.java file from it.rate package in editor
    - find at line 31 following code:
            public static final String REDIRECTION_URL = "http://rateerate.appspot.com/";  
    - replace it with 
            public static final String REDIRECTION_URL = "http://your-domain.com/";  
            
4) Deploy it to Google App Engine


##License

This program, plugin, or function is licenced under the Maximal Opensource Licence. That means that it`s source should always stay open source. And any changes must be available with the branches. And its derivatives can never in anyway be released as closed source.

Authorship can be included, but it is not neccesary. A list of changes by author is though recommended.