--Nếu CSDL test đã tồn tại thì xóa nó đi
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'test' )
	DROP DATABASE test
GO
--Tạo một CSDL có tên là test
CREATE DATABASE test
GO
USE test
GO
--Tạo bảng Class
CREATE TABLE Class(
	ID INT PRIMARY KEY IDENTITY,
	Name VARCHAR(10),
	Deleted INT NOT NULL DEFAULT (0)
)
GO
--Tạo bảng Student
CREATE TABLE Student(
	ID INT PRIMARY KEY IDENTITY,
	Name VARCHAR(30),
	Age INT,
	ClassID INT FOREIGN KEY REFERENCES Class(ID),
	Deleted INT NOT NULL DEFAULT (0)
)

GO
	INSERT INTO Class (Name) VALUES ('C1101I')
GO
--Tạo một INSERT trigger nhằm đảm bảo giá trị của cột tuổi khi chèn vào là luôn luôn lớn hơn 16
CREATE TRIGGER CheckAgeOnInsert
ON Student
FOR INSERT
AS 
	BEGIN
		IF EXISTS(SELECT * FROM inserted WHERE Age<16)
		BEGIN
			PRINT 'Tuoi khong the nho hon 16';
			ROLLBACK TRANSACTION;
		END
	END
GO
--Kiểm tra sự hoạt động của INSERT trigger vừa tạo ở trên
--Câu lệnh sau sẽ không thể chèn vào vì có tuổi nhỏ hơn 16
INSERT INTO Student VALUES('Nguyen Van Teo', 15, 1, 0)

GO
--Tạo một UPDATE trigger nhằm đảm bảo rằng khi tiến hành cập nhật dữ liệu thì tuổi mới
--phải luôn lớn hơn tuổi cũ
CREATE TRIGGER CheckAgeOnUpdate
ON Student
FOR UPDATE
AS
	BEGIN
		IF EXISTS(SELECT * FROM inserted I INNER JOIN deleted D 
			ON I.ID=D.ID WHERE D.Age>I.Age)
		BEGIN
			PRINT 'Tuoi moi khong the nho hon tuoi cu';
			ROLLBACK TRANSACTION;
		END
	END
GO
--Kiểm tra sự hoạt động của trigger vừa tạo.
-- Chèn một sinh viên có tuổi là 20 vào bảng Student
INSERT INTO Student VALUES('Nguyen Van Teo', 20, 1, 0)
--Tiến hành cập nhật tuổi cho sinh viên trên, câu lệnh này sẽ cập nhật được tuổi của sinh viên
--Bởi vì tuổi mới là 19, trong khi đó tuổi cũ là 20
UPDATE Student SET Age = 19 WHERE Name LIKE 'Nguyen Van Teo';
GO

--Tạo một DELETE trigger nhằm không cho phép xóa hẳn một student khỏi bảng Student,
--Thay vào đó ta sẽ chuyển giá trị của cột Deleted thành 1
CREATE TRIGGER DeleteStudent
ON Student
FOR DELETE
AS
	BEGIN
		DECLARE @ID int;		
		SELECT @ID = ID FROM deleted;
		ROLLBACK TRANSACTION;
		UPDATE Student SET Deleted=1 WHERE ID=@ID;
	END
GO
--Kiểm tra sự hoạt động của trigger vừa tạo
--INSERT INTO Student VALUES('Teo', 20, 1)
-- Thực hiện xóa một sinh viên có ID là 1, sau khi thực hiện câu lệnh bên dưới thì
--bản ghi của sinh viên này không bị xóa đi, mà thay vào đó thì giá trị của cột Deleted
-- của bản ghi này sẽ có giá trị là 1

---Trigger ap dung cho xoa nhieu ban ghi
ALTER TRIGGER DeleteStudent
ON Student
FOR DELETE
AS
	BEGIN
		UPDATE Student SET Deleted=1 WHERE ID IN (SELECT ID FROM deleted);
		ROLLBACK TRANSACTION;
	END
GO

DELETE FROM Student WHERE ID=1
GO
--Tạo một INSTEAD OF trigger nhằm đảm bảo rằng khi ta xóa một lớp ra khỏi bảng Class
-- thì đồng thời ta cũng xóa đi tất cả các sinh viên của lớp đó (thực sự thì các sinh viên
-- này chỉ bị chuyển sang trạng thái Delete=1 mà thôi)
CREATE TRIGGER DeleteClass
ON Class
INSTEAD OF DELETE
AS
	BEGIN
		DELETE FROM Student WHERE ClassID IN (SELECT ID FROM deleted);
		UPDATE Class SET Deleted=1 WHERE ID IN (SELECT ID FROM deleted);
	END
	
GO
--Kiểm tra sự hoạt động của trigger vừa tạo
--Hiển thị danh sách các lớp, danh sách sinh viên trước khi thực hiện thao tác xóa
select * from Class
select * from Student
--Tiến hành xóa một lớp, sau khi thực thi xong thì quay lại thực hiện câu lệnh 
-- để quan sát kết quả thu được
DELETE FROM Class WHERE ID = 1

--Trigger voi VIEW:
CREATE VIEW vStudent AS
SELECT ID, Name, Deleted FROM Student
--Chi co the tao trigger tren view voi INSTEAD OF
CREATE ALTER TRIGGER tgDelete_vStudent
ON vStudent INSTEAD OF DELETE
AS BEGIN
	BEGIN
		ROLLBACK TRANSACTION;
		UPDATE Student SET Deleted=1 WHERE ID IN (SELECT ID FROM deleted);
	END
END
--Kiem tra VIEW
DELETE FROM vStudent WHERE ID=2


--Hiển thị định nghĩa của một Trigger
EXEC sp_helptext 'DeleteStudent'

GO
--Tạo một DDL trigger nhằm ngăn cản việc tạo một bảng mới trong CSDL hiện tại
CREATE TRIGGER Create_Permission
ON DATABASE
FOR CREATE_TABLE
AS
	BEGIN
		PRINT 'Ban khong duoc tạo bang o day'
		ROLLBACK
	END
	
GO
-- Kiểm tra sự hoạt động của trigger trên
CREATE TABLE Test
(
	Test int
)
GO
--Tạo một DDL trigger để ngăn cản việc xóa một CSDL khỏi hệ thống
CREATE TRIGGER Drop_Permission
ON ALL SERVER
FOR DROP_DATABASE
AS
	BEGIN
		PRINT 'Ban khong duoc xoa database'
		ROLLBACK
	END

--Kiểm tra sự hoạt động của trigger trên
GO
use master
GO
--Tiến hành xóa CSDL hiện tại là 'test'
DROP DATABASE test

--Xem thông tin chi tiết của DDL trigger
SELECT * FROM sys.triggers WHERE name='Create_Permission'

