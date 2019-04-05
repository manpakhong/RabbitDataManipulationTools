echo off
FOR /D %%A IN (A B C D E F G H I J K L M N O P Q R S T U V W X Y Z) DO (
  IF EXIST %%A:\flowtestNewDb\checkdrive (
	%%A:\flowtestOldDb\jdk-1.8-ea\bin\java.exe -cp .;%%A:\flowtestNewDb\cssatesting-jar-with-dependencies.jar hksarg.swd.csss.csa.flowtest.views.EntranceView
  )
)