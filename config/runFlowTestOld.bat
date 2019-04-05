echo off
FOR /D %%A IN (C D E F G H I J K L M N O P Q R S T U V W X Y Z) DO (
  IF EXIST %%A:\flowTestOldDb\checkolddbdrive (
	%%A:\flowTestOldDb\jdk-1.8-ea\bin\java.exe -cp .;%%A:\flowTestOldDb\jcl-over-slf4j-1.7.21.jar;%%A:\flowTestOldDb\slf4j-api-1.7.21.jar;%%A:\flowTestOldDb\slf4j-log4j12-1.7.21.jar;%%A:\flowTestOldDb\jcl-over-slf4j-1.7.21.jar;%%A:\flowTestOldDb\cssatesting-jar-with-dependencies.jar hksarg.swd.csss.csa.flowtest.views.EntranceView
  )
)