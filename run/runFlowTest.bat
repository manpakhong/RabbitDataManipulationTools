echo off
FOR /D %%A IN (A B C D E F G H I J K L M N O P Q R S T U V W X Y Z) DO (
  IF EXIST %%A:\DataManipulationToolsNew\checkdrive (
	%%A:\DataManipulationToolsNew\jdk-1.8-ea\bin\java.exe -cp .;%%A:\DataManipulationToolsNew\jcl-over-slf4j-1.7.21.jar;%%A:\DataManipulationToolsNew\slf4j-api-1.7.21.jar;%%A:\DataManipulationToolsNew\slf4j-log4j12-1.7.21.jar;%%A:\DataManipulationToolsNew\jcl-over-slf4j-1.7.21.jar;%%A:\DataManipulationToolsNew\RabbitDataManipulationTools-jar-with-dependencies.jar com.rabbitforever.datamanipulation.commands.RunBatchScopesCapture
  )
)