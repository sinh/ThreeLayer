USE master
GO
IF EXISTS(SELECT * FROM sys.databases WHERE name='LabDB')
	DROP DATABASE LabDB
GO
CREATE DATABASE LabDB
GO
USE LabDB
GO
CREATE TABLE Classes
(
	ClassName varchar(15) CONSTRAINT PK_Classes PRIMARY KEY,
	[Location] nvarchar(100),
)
CREATE TABLE Students
(
	RollNo varchar(6) CONSTRAINT PK_Students PRIMARY KEY,
	FullName nvarchar(50) NOT NULL,
	Birthday datetime,
	[Address] nvarchar(100),
	ClassName varchar(15) CONSTRAINT FK_Students_Classes FOREIGN KEY REFERENCES Classes(ClassName) ON UPDATE CASCADE
)
CREATE TABLE Subjects
(
	SubjectID varchar(15) CONSTRAINT PK_Subjects PRIMARY KEY,
	SubjectName nvarchar(50) NOT NULL CONSTRAINT UQ_Subject_SubjectName UNIQUE,
	TheoryNo int CONSTRAINT CK_Subject_TheoryNo CHECK(TheoryNo>0),
	ParticeNo int CONSTRAINT CK_Subject_ParticeNo CHECK(ParticeNo>0),
)
CREATE TABLE Marks
(
	RollNo varchar(6) CONSTRAINT FK_Marks_Students FOREIGN KEY REFERENCES Students(RollNo),
	SubjectID varchar(15) CONSTRAINT FK_Marks_Subjects FOREIGN KEY REFERENCES Subjects(SubjectID),
	Mark float CONSTRAINT CK_Mark CHECK (Mark>=0 OR Mark<=25),
	CONSTRAINT PK_Marks_Mark PRIMARY KEY(RollNo, SubjectID)
)
GO
--INSERT DATA
INSERT INTO Classes VALUES('T1107G', 'FAT3')
INSERT INTO Classes VALUES('T1105L', 'FAT1')
INSERT INTO Classes VALUES('T1109L', 'FAT1')
INSERT INTO Classes VALUES('C1105I', 'FAT3')
INSERT INTO Students VALUES('C00111', N'Nguyen Van A', '12/12/1987', N'Ha Noi', 'T1107G')
INSERT INTO Students VALUES('C00112', N'Nguyen Van B', '12/12/1987', N'Ha Noi', 'T1107G')
INSERT INTO Students VALUES('C00113', N'Nguyen Van C', '12/12/1987', N'Ha Noi', 'T1107G')
INSERT INTO Students VALUES('C00114', N'Nguyen Van D', '12/12/1987', N'Ha Noi', 'T1107G')
INSERT INTO Students VALUES('A00111', N'Tran Thi X', '12/12/1987', N'Ha Noi', 'T1105L')
INSERT INTO Students VALUES('A00112', N'Tran Thi Y', '12/12/1987', N'Ha Noi', 'T1105L')
INSERT INTO Students VALUES('A00113', N'Pham Van O', '12/12/1987', N'Ha Noi', 'T1105L')
INSERT INTO Students VALUES('A00114', N'Pham Van P', '12/12/1987', N'Ha Noi', 'T1105L')
INSERT INTO Students VALUES('C00115', N'Pham Van Q', '12/12/1987', N'Ha Noi', 'C1105I')
INSERT INTO Students VALUES('C00116', N'Pham Van R', '12/12/1987', N'Ha Noi', 'C1105I')
INSERT INTO Students VALUES('C00117', N'Tran Thi Z', '12/12/1987', N'Ha Noi', 'C1105I')
INSERT INTO Subjects VALUES('CF', 'Computer Fundamental', 5, 5)
INSERT INTO Subjects VALUES('BDWS', 'Building Dinamic Website', 13, 13)
INSERT INTO Subjects VALUES('EPC', 'Elementary Programing with C', 14, 14)
INSERT INTO Subjects VALUES('MSSQL', 'Development Database with MS SQL Server', 13, 11)
INSERT INTO Subjects VALUES('APJ1', 'Advance Programing Java 1', 9, 9)
INSERT INTO Marks VALUES('C00111', 'CF', 14)
INSERT INTO Marks VALUES('C00112', 'CF', 9)
INSERT INTO Marks VALUES('C00113', 'CF', 22)
INSERT INTO Marks VALUES('C00114', 'CF', 11)
INSERT INTO Marks VALUES('C00115', 'CF', 7)
INSERT INTO Marks VALUES('C00116', 'CF', 20)
INSERT INTO Marks VALUES('C00117', 'CF', 24)
INSERT INTO Marks VALUES('A00111', 'CF', 23.5)
INSERT INTO Marks VALUES('A00112', 'CF', 14.75)
INSERT INTO Marks VALUES('A00113', 'CF', 16)
INSERT INTO Marks VALUES('A00114', 'CF', 18.5)
INSERT INTO Marks VALUES('C00111', 'EPC', 14)
INSERT INTO Marks VALUES('C00112', 'EPC', 9)
INSERT INTO Marks VALUES('C00113', 'EPC', 22)
INSERT INTO Marks VALUES('C00114', 'BDWS', 11)
INSERT INTO Marks VALUES('C00115', 'MSSQL', 7)
INSERT INTO Marks VALUES('C00116', 'BDWS', 20)
INSERT INTO Marks VALUES('C00117', 'MSSQL', 24)
INSERT INTO Marks VALUES('A00111', 'BDWS', 23.5)
INSERT INTO Marks VALUES('A00112', 'EPC', 14.75)
INSERT INTO Marks VALUES('A00113', 'MSSQL', 16)
INSERT INTO Marks VALUES('A00114', 'EPC', 18.5)
GO

--Create STORE PROCEDURE
---INSERT Classes
CREATE PROCEDURE spInsertClasses
	@ClassName varchar(15),
	@Location nvarchar(100)
	--@IsInsert int OUTPUT
AS BEGIN
	IF EXISTS(SELECT ClassName FROM Classes WHERE ClassName=@ClassName)
		--SET @IsInsert=0
		RETURN 1
	ELSE BEGIN
		INSERT INTO Classes(ClassName, Location) VALUES(@ClassName, @Location)
		--SET @IsInsert=1
		RETURN 0
	END
END
GO
---Delete Classes
CREATE PROCEDURE spDeleteClasses
	@ClassName varchar(15)
AS BEGIN
	IF EXISTS(SELECT ClassName FROM Classes WHERE ClassName=@ClassName)
	BEGIN
		DELETE FROM Marks WHERE RollNo IN (SELECT RollNo FROM Students WHERE ClassName=@ClassName)
		DELETE FROM Students WHERE ClassName=@ClassName
		DELETE FROM Classes WHERE ClassName=@ClassName
		RETURN 0
	END
	ELSE
		RETURN 1
END
GO
---Update Classes
CREATE PROCEDURE spUpdateClasses
	@OldName varchar(15),
	@NewName varchar(15),
	@Location nvarchar(100)
AS BEGIN
	IF EXISTS(SELECT ClassName FROM Classes WHERE ClassName=@OldName)
	BEGIN
		IF EXISTS(SELECT ClassName FROM Classes WHERE ClassName=@NewName)
			RETURN 2
		ELSE BEGIN
			UPDATE Classes SET ClassName=@NewName, Location=@Location WHERE ClassName=@OldName
			RETURN 0
		END
	END
	ELSE
		RETURN 1
END
GO
--- ***** _____ CREATE USER _____ ***** ---
USE master
GO
IF EXISTS (SELECT * FROM syslogins WHERE name='sinhnx')
BEGIN
	EXEC sp_droplogin 'sinhnx'
END
GO
EXEC sp_addlogin 'sinhnx', 'fat123456', 'LabDB'
GO
USE LabDB
GO
EXEC sp_grantdbaccess 'sinhnx'
GO
--Phan quyen
GRANT INSERT, UPDATE, DELETE, SELECT ON Classes TO sinhnx
GRANT INSERT, UPDATE, DELETE, SELECT ON Students TO sinhnx
GRANT INSERT, UPDATE, DELETE, SELECT ON Subjects TO sinhnx
GRANT INSERT, UPDATE, DELETE, SELECT ON Marks TO sinhnx
GRANT EXECUTE ON spInsertClasses TO sinhnx
GRANT EXECUTE ON spUpdateClasses TO sinhnx
GRANT EXECUTE ON spDeleteClasses TO sinhnx
GO

SELECT * FROM Classes
