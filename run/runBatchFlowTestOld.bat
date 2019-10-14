echo off
FOR /D %%A IN (C D E F G H I J K L M N O P Q R S T U V W X Y Z) DO (
  IF EXIST %%A:\DataManipulationToolsOld\checkolddbdrive (
	%%A:\DataManipulationToolsOld\jdk-1.8-ea\bin\java.exe -cp .;%%A:\DataManipulationToolsOld\jcl-over-slf4j-1.7.21.jar;%%A:\DataManipulationToolsOld\slf4j-api-1.7.21.jar;%%A:\DataManipulationToolsOld\slf4j-log4j12-1.7.21.jar;%%A:\DataManipulationToolsOld\jcl-over-slf4j-1.7.21.jar;%%A:\DataManipulationToolsOld\RabbitDataManipulationTools-jar-with-dependencies.jar com.rabbitforever.datamanipulation.commands.RunBatchScopesCapture
  )
)