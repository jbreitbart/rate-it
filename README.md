rate-it
=======

Rate it!

http://rateerate.appspot.com/


Informations
============
adjustments
-----------
If you want to deploy the code to a different site you have to change some lines in the code.
/war/WEB-INF/appengine-web.xml: application tag
/src/it/rate/Constants.java: REDIRECTION_URL value

benchmark
---------
In order to test the performance of simultaneous ratings you can perform workload tests. To do so, you have to open the following site:
www.rateerate.appspot.com/queue/createratingtasks?jobs=1000&parts=10\n
whereas jobs means the number of tasks which are put in the queues.\n
Parts means how many queues are started to proceed all these tasks. This number is important, because putting to many tasks in one queue can cause a timeout.
In example 1000 tasks put in 10 queues means every queue gets 100 tasks to execute.
If you want to change queue settings you can do that in queue.xml.

