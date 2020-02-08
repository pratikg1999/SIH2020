# Home_Automation
Controls the home appliances remotely using firebase, a raspberry pie at home and a simple app.

## The app 
This repository contains the app of the system.

An account has to be created to access the applicances at home. There are two type of accounts, an admin or a member account. There can only be one admin and each member has to be verified by that admin to acess the system.

After logging-in on the app, the user sees a dashboard where he/she can see the current status of the appliances and control them.

The on/off request is sent to the firebase realtime database so that other devices/members can get the right status of the system.

The python script running on the raspberry-pie detects the changes in the database and accordingly turns the device on/off.

#### To run a prebuilt apk download [this](app-debug.apk) apk.
