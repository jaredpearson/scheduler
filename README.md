# Scheduler

This is a priority based scheduler. The main purpose of building this application was a study in multi-threading.

# Running the application

Main execution class is 
javac -cp scheduler.jar scheduler2.App

# Sample output

```
[1] Starting scheduler
[8] Scheduler started
[8] Starting job: 0
[9] Job starting
[9] Job will take 5000 millis
[10] Interrupting current job due to higher priority job added
[9] Job interrupted 1490 millis in. Need 3510 more millis to complete
[8] Starting job: 2
[11] Job starting
[11] Job will take 5000 millis
[11] Job finished
[8] Finished job: 2
[8] Starting job: 0
[12] Job starting
[12] Job will take 3510 millis
[12] Job finished
[8] Finished job: 0
[8] Starting job: 1
[13] Job starting
[13] Job will take 5000 millis
[13] Job finished
[8] Finished job: 1
[1] Stopping scheduler
[8] Scheduler stopped
```