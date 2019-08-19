# Telemetry
### An Android app for data acquisition and sensor logging.

![cover](https://github.com/CeuAzul/Telemetry/blob/master/assets/screen.png) 


### [Paper (portuguese)](https://github.com/CeuAzul/Telemetry/blob/master/assets/smartphone_paper.pdf)  | [Google Play link](https://play.google.com/store/apps/details?id=whitesun.telemetry)

This paper was published in the 6º Fórum SAE Brasil Aerodesign

> **Smartphone: An affordable alternative for UAV data acquisition**<br>
> **Leonardo Mariga (UFSC)**, Rafael Araújo Lehmkuhl (UFSC), Prof. Amir Antonio Martins Oliveira Jr., Ph.D. (UFSC)
>
> **Abstract:** *Smartphones have a wide range of sensors, such as GPS, gyroscope, accelerometer, magnetometer and barometer, which are used on a daily basis for orientation, location and comfort of its user. This paper describes the development of an Android app that serves as an UAV data acquisition system. The paper shows a detailed description of the developed software and analysis of experimental data. It also evaluates system performance by comparing it to other acquisition systems in terms of accuracy and speed. Finally, it shows results of Céu Azul team using this app as an aid for designing aircrafts.*

## How to contribute

- Fork the project
- Open Android Studio
- Select Check out project from Version Control
- Select the project from your respositories list (make sure you have logged onto Android Studio's Github integration)

## How does it work?

Telemetry access sensors inside a smartphone enabling you to log data from all of them. Most smarphones have accelerometers, gyroscopes, GPS, light sensor, temperature sensor, magnetometers etc. When the log begins, it uses the first GPS coordinates as a home location, calculating the relative position in the log file. The high-level block diagram is shown below: 


![wscheme](https://github.com/CeuAzul/Telemetry/blob/master/assets/howorks.png) 

## Are smartphones good enough for data aquisition?

For many application, **yes!** Most sensors available in smartphones have similar characteristics of resolution, range and speed to the ones used in dedicated acquisition platforms :grin:. Furthermore, the processing power of smartphones are usually higher then many platforms allowing the user to acquire data faster, or use more complex algorithims to filter and fuse data. 

Smartphone vs dedicated acquisition systems             |  Quality of smartphone
:-------------------------:|:-------------------------:
![](https://github.com/CeuAzul/Telemetry/blob/master/assets/platform.png)  |  ![](https://github.com/CeuAzul/Telemetry/blob/master/assets/models.png)

## Tests
Flight tests using both [T2015](https://github.com/CeuAzul/Preludio) data acquisition platform and a Galaxy S inside UAVs from Céu Azul.


![](https://github.com/CeuAzul/Telemetry/blob/master/assets/accy.png)  |  ![](https://github.com/CeuAzul/Telemetry/blob/master/assets/speed.png)
:-------------------------:|:-------------------------:
![](https://github.com/CeuAzul/Telemetry/blob/master/assets/trajec.png)  |  ![](https://github.com/CeuAzul/Telemetry/blob/master/assets/attitude.png)

## Contact
Developed with :heart: by [Leonardo Mariga](https://github.com/leomariga)

*leomariga@gmail.com*

**Céu Azul Aeronaves**

*ceuazulufsc@gmail.com*

**Special thanks to:** Rafael Araújo Lehmkuhl, Lucas Andriolli and Céu Azul Team. 