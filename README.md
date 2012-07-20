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
            
4) Change some lines in the code.  

    /war/WEB-INF/appengine-web.xml: application tag  

5) Deploy the application to Google App Engine.


Informations
============


Benchmark
---------
In order to test the performance of simultaneous ratings you can perform workload tests. To do so, you have to open the following site:
www.rateerate.appspot.com/queue/createratingtasks?jobs=1000&parts=10  
whereas jobs means the number of tasks which are put in the queues.  
Parts means how many queues are started to proceed all these tasks. This number is important, because putting to many tasks in one queue can cause a timeout.  
In example 1000 tasks put in 10 queues means every queue gets 100 tasks to execute.  
If you want to change queue settings you can do that in queue.xml.  


##License

This program, plugin, or function is licenced under the Maximal Opensource Licence. That means that it`s source should always stay open source. And any changes must be available with the branches. And its derivatives can never in anyway be released as closed source.

Authorship can be included, but it is not neccesary. A list of changes by author is though recommended.