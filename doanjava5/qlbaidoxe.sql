create database QuanLyBaiGuiXe;

CREATE LOGIN QuanLyBaiGuiXe WITH PASSWORD = '123456';
GO
USE QuanLyBaiGuiXe;
GO
CREATE USER QuanLyBaiGuiXe FOR LOGIN QuanLyBaiGuiXe;
GO
ALTER ROLE db_owner ADD MEMBER QuanLyBaiGuiXe;
GO

CREATE TABLE KhachHang (
    MaKH    INT IDENTITY(1,1) PRIMARY KEY,
    HoTen   NVARCHAR(100) NOT NULL,
    SDT     VARCHAR(15)   NOT NULL,
    CCCD    VARCHAR(12)   NULL,
    DiaChi  NVARCHAR(200) NULL
);

CREATE TABLE LoaiXe (
    MaLoai  INT IDENTITY(1,1) PRIMARY KEY,
    TenLoai NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Xe (
    MaXe    INT IDENTITY(1,1) PRIMARY KEY,
    BienSo  VARCHAR(15) NOT NULL UNIQUE,
    MaLoai  INT NOT NULL REFERENCES LoaiXe(MaLoai),
    MaKH    INT NULL REFERENCES KhachHang(MaKH),   
    MauSac  NVARCHAR(30) NULL,
    HangXe  NVARCHAR(50) NULL
);

CREATE TABLE ViTri (
    MaViTri   INT IDENTITY(1,1) PRIMARY KEY,
    TenViTri  NVARCHAR(20) NOT NULL UNIQUE,        
    KhuVuc    NVARCHAR(30) NOT NULL,
    Tang      INT NOT NULL DEFAULT 1,
    MaLoai    INT NOT NULL REFERENCES LoaiXe(MaLoai),
    TrangThai NVARCHAR(20) NOT NULL DEFAULT N'Trống',
    CONSTRAINT CK_ViTri_TrangThai CHECK (TrangThai IN (N'Trống', N'Có xe', N'Bảo trì'))
);

CREATE TABLE BangGia (
    MaGia       INT IDENTITY(1,1) PRIMARY KEY,
    MaLoai      INT NOT NULL REFERENCES LoaiXe(MaLoai),
    HinhThuc    NVARCHAR(10) NOT NULL,
    DonGia      DECIMAL(12,0) NOT NULL CHECK (DonGia >= 0),
    NgayApDung  DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    NgayKetThuc DATE NULL,
    CONSTRAINT CK_BangGia_HinhThuc CHECK (HinhThuc IN (N'Lượt', N'Giờ', N'Ngày', N'Tháng')),
    CONSTRAINT CK_BangGia_Ngay CHECK (NgayKetThuc IS NULL OR NgayKetThuc >= NgayApDung)
);

CREATE TABLE NhanVien (
    MaNV        INT IDENTITY(1,1) PRIMARY KEY,
    HoTen       NVARCHAR(100) NOT NULL,
    SDT         VARCHAR(15) NULL,
    ChucVu      NVARCHAR(30) NOT NULL DEFAULT N'Bảo vệ',
    TaiKhoan    VARCHAR(50) NOT NULL UNIQUE,
    MatKhauHash VARCHAR(255) NOT NULL             
);

CREATE TABLE CaTruc (
    MaCa       INT IDENTITY(1,1) PRIMARY KEY,
    MaNV       INT NOT NULL REFERENCES NhanVien(MaNV),
    GioBatDau  DATETIME NOT NULL,
    GioKetThuc DATETIME NULL,
    CONSTRAINT CK_CaTruc_Gio CHECK (GioKetThuc IS NULL OR GioKetThuc > GioBatDau)
);

CREATE TABLE VeThang (
    MaVe       INT IDENTITY(1,1) PRIMARY KEY,
    MaKH       INT NOT NULL REFERENCES KhachHang(MaKH),
    MaXe       INT NOT NULL REFERENCES Xe(MaXe),
    NgayBatDau DATE NOT NULL,
    NgayHetHan DATE NOT NULL,
    GiaVe      DECIMAL(12,0) NOT NULL CHECK (GiaVe >= 0),
    TrangThai  NVARCHAR(20) NOT NULL DEFAULT N'Còn hạn',
    CONSTRAINT CK_VeThang_Ngay CHECK (NgayHetHan > NgayBatDau),
    CONSTRAINT CK_VeThang_TT CHECK (TrangThai IN (N'Còn hạn', N'Hết hạn', N'Đã huỷ'))
);

CREATE TABLE LuotGui (
    MaLuot    INT IDENTITY(1,1) PRIMARY KEY,
    MaXe      INT NOT NULL REFERENCES Xe(MaXe),
    MaViTri   INT NOT NULL REFERENCES ViTri(MaViTri),
    MaVe      INT NULL REFERENCES VeThang(MaVe),  
    GioVao    DATETIME NOT NULL DEFAULT GETDATE(),
    GioRa     DATETIME NULL,
    NV_Vao    INT NOT NULL REFERENCES NhanVien(MaNV),
    NV_Ra     INT NULL REFERENCES NhanVien(MaNV),
    TrangThai NVARCHAR(20) NOT NULL DEFAULT N'Đang gửi',
    GhiChu    NVARCHAR(200) NULL,
    CONSTRAINT CK_LuotGui_Gio CHECK (GioRa IS NULL OR GioRa >= GioVao),
    CONSTRAINT CK_LuotGui_TT  CHECK (TrangThai IN (N'Đang gửi', N'Đã ra'))
);

CREATE TABLE ThanhToan (
    MaTT       INT IDENTITY(1,1) PRIMARY KEY,
    MaLuot     INT NULL REFERENCES LuotGui(MaLuot),
    MaVe       INT NULL REFERENCES VeThang(MaVe),
    SoTien     DECIMAL(12,0) NOT NULL CHECK (SoTien >= 0),
    PhuongThuc NVARCHAR(20) NOT NULL DEFAULT N'Tiền mặt',
    ThoiGian   DATETIME NOT NULL DEFAULT GETDATE(),
    MaNV       INT NOT NULL REFERENCES NhanVien(MaNV),
    CONSTRAINT CK_TT_PhuongThuc CHECK (PhuongThuc IN (N'Tiền mặt', N'Chuyển khoản', N'Ví điện tử')),
    CONSTRAINT CK_TT_DoiTuong CHECK (
        (MaLuot IS NOT NULL AND MaVe IS NULL) OR (MaLuot IS NULL AND MaVe IS NOT NULL))
);

CREATE TABLE SuCo (
    MaSC      INT IDENTITY(1,1) PRIMARY KEY,
    MaLuot    INT NOT NULL REFERENCES LuotGui(MaLuot),
    LoaiSuCo  NVARCHAR(50) NOT NULL,               
    MoTa      NVARCHAR(500) NULL,
    ThoiGian  DATETIME NOT NULL DEFAULT GETDATE(),
    HuongXuLy NVARCHAR(500) NULL
);
GO

CREATE UNIQUE INDEX UX_LuotGui_Xe_DangGui ON LuotGui(MaXe)
    WHERE TrangThai = N'Đang gửi';

CREATE UNIQUE INDEX UX_LuotGui_ViTri_DangGui ON LuotGui(MaViTri)
    WHERE TrangThai = N'Đang gửi';

CREATE INDEX IX_LuotGui_GioVao    ON LuotGui(GioVao);
CREATE INDEX IX_LuotGui_TrangThai ON LuotGui(TrangThai);
CREATE INDEX IX_VeThang_MaXe      ON VeThang(MaXe, NgayHetHan);
CREATE INDEX IX_ThanhToan_ThoiGian ON ThanhToan(ThoiGian);
GO


INSERT INTO LoaiXe(TenLoai) VALUES (N'Xe máy'), (N'Ô tô'), (N'Xe đạp');

INSERT INTO NhanVien(HoTen, SDT, ChucVu, TaiKhoan, MatKhauHash) VALUES
(N'Nguyễn Văn An', '0901000001', N'Bảo vệ',  'an',   'HASH_DEMO_1'),
(N'Trần Thị Bình', '0901000002', N'Thu ngân', 'binh', 'HASH_DEMO_2');

INSERT INTO KhachHang(HoTen, SDT, CCCD, DiaChi) VALUES
(N'Lê Minh Cường', '0912345678', '001099000001', N'Cầu Giấy, Hà Nội'),
(N'Phạm Thu Dung', '0987654321', '001099000002', N'Đống Đa, Hà Nội');

INSERT INTO Xe(BienSo, MaLoai, MaKH, MauSac, HangXe) VALUES
('29A1-123.45', 1, 1, N'Đen',   N'Honda'),
('30G-678.90',  2, 2, N'Trắng', N'Toyota'),
('29B2-111.22', 1, NULL, N'Đỏ', N'Yamaha');     


INSERT INTO ViTri(TenViTri, KhuVuc, Tang, MaLoai) VALUES
('A1-01', N'Khu A', 1, 1), ('A1-02', N'Khu A', 1, 1), ('A1-03', N'Khu A', 1, 1),
('A1-04', N'Khu A', 1, 1), ('A1-05', N'Khu A', 1, 1),
('B1-01', N'Khu B', 1, 2), ('B1-02', N'Khu B', 1, 2), ('B1-03', N'Khu B', 1, 2);

INSERT INTO BangGia(MaLoai, HinhThuc, DonGia) VALUES
(1, N'Giờ', 3000),   (1, N'Ngày', 15000),  (1, N'Tháng', 120000),
(2, N'Giờ', 20000),  (2, N'Ngày', 100000), (2, N'Tháng', 1500000),
(3, N'Giờ', 1000),   (3, N'Ngày', 5000),   (3, N'Tháng', 50000);

INSERT INTO VeThang(MaKH, MaXe, NgayBatDau, NgayHetHan, GiaVe) VALUES
(1, 1, DATEADD(DAY, -10, CAST(GETDATE() AS DATE)), DATEADD(DAY, 20, CAST(GETDATE() AS DATE)), 120000);
GO

CREATE OR ALTER PROCEDURE sp_XeVao
    @BienSo VARCHAR(15),
    @MaLoai INT,
    @MaNV   INT
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;
    BEGIN TRAN;
    DECLARE @MaXe INT = (SELECT MaXe FROM Xe WHERE BienSo = @BienSo);
    IF @MaXe IS NULL
    BEGIN
        INSERT INTO Xe(BienSo, MaLoai) VALUES (@BienSo, @MaLoai);
        SET @MaXe = SCOPE_IDENTITY();
    END

    IF EXISTS (SELECT 1 FROM LuotGui WHERE MaXe = @MaXe AND TrangThai = N'Đang gửi')
    BEGIN
        ROLLBACK; THROW 50001, N'Xe này đang trong bãi.', 1;
    END

    DECLARE @MaViTri INT = (
        SELECT TOP 1 MaViTri FROM ViTri WITH (UPDLOCK, ROWLOCK)
        WHERE MaLoai = @MaLoai AND TrangThai = N'Trống'
        ORDER BY MaViTri);
    IF @MaViTri IS NULL
    BEGIN
        ROLLBACK; THROW 50002, N'Hết chỗ trống cho loại xe này.', 1;
    END
    DECLARE @MaVe INT = (
        SELECT TOP 1 MaVe FROM VeThang
        WHERE MaXe = @MaXe AND TrangThai = N'Còn hạn'
          AND CAST(GETDATE() AS DATE) BETWEEN NgayBatDau AND NgayHetHan);

    INSERT INTO LuotGui(MaXe, MaViTri, MaVe, NV_Vao) VALUES (@MaXe, @MaViTri, @MaVe, @MaNV);
    DECLARE @MaLuot INT = SCOPE_IDENTITY();

    UPDATE ViTri SET TrangThai = N'Có xe' WHERE MaViTri = @MaViTri;
    COMMIT;

    SELECT @MaLuot AS MaLuot, @MaViTri AS MaViTri, @MaVe AS MaVe;
END
GO

CREATE OR ALTER PROCEDURE sp_XeRa
    @MaLuot     INT,
    @MaNV       INT,
    @PhuongThuc NVARCHAR(20) = N'Tiền mặt'
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;
    BEGIN TRAN;

    DECLARE @MaXe INT, @MaVe INT, @MaViTri INT, @GioVao DATETIME, @MaLoai INT;
    SELECT @MaXe = l.MaXe, @MaVe = l.MaVe, @MaViTri = l.MaViTri, @GioVao = l.GioVao, @MaLoai = x.MaLoai
    FROM LuotGui l JOIN Xe x ON x.MaXe = l.MaXe
    WHERE l.MaLuot = @MaLuot AND l.TrangThai = N'Đang gửi';

    IF @MaXe IS NULL
    BEGIN
        ROLLBACK; THROW 50003, N'Không tìm thấy lượt gửi đang hoạt động.', 1;
    END

    DECLARE @Now DATETIME = GETDATE();
    DECLARE @SoTien DECIMAL(12,0) = 0;

    IF @MaVe IS NULL
    BEGIN
        DECLARE @GiaGio DECIMAL(12,0) = (SELECT TOP 1 DonGia FROM BangGia
            WHERE MaLoai = @MaLoai AND HinhThuc = N'Giờ'
              AND CAST(@Now AS DATE) >= NgayApDung
              AND (NgayKetThuc IS NULL OR CAST(@Now AS DATE) <= NgayKetThuc)
            ORDER BY NgayApDung DESC);
        DECLARE @GiaNgay DECIMAL(12,0) = (SELECT TOP 1 DonGia FROM BangGia
            WHERE MaLoai = @MaLoai AND HinhThuc = N'Ngày'
              AND CAST(@Now AS DATE) >= NgayApDung
              AND (NgayKetThuc IS NULL OR CAST(@Now AS DATE) <= NgayKetThuc)
            ORDER BY NgayApDung DESC);

        DECLARE @Gio INT = CEILING(DATEDIFF(MINUTE, @GioVao, @Now) / 60.0);
        IF @Gio < 1 SET @Gio = 1;

        DECLARE @Ngay INT = @Gio / 24, @GioLe INT = @Gio % 24;
        DECLARE @PhiGioLe DECIMAL(12,0) = @GioLe * @GiaGio;
        IF @GiaNgay IS NOT NULL AND @PhiGioLe > @GiaNgay SET @PhiGioLe = @GiaNgay;

        SET @SoTien = ISNULL(@Ngay * @GiaNgay, 0) + @PhiGioLe;

        INSERT INTO ThanhToan(MaLuot, SoTien, PhuongThuc, MaNV) VALUES (@MaLuot, @SoTien, @PhuongThuc, @MaNV);
    END

    UPDATE LuotGui SET GioRa = @Now, NV_Ra = @MaNV, TrangThai = N'Đã ra' WHERE MaLuot = @MaLuot;
    UPDATE ViTri   SET TrangThai = N'Trống' WHERE MaViTri = @MaViTri;
    COMMIT;

    SELECT @MaLuot AS MaLuot, @GioVao AS GioVao, @Now AS GioRa, @SoTien AS SoTien;
END
GO


CREATE OR ALTER PROCEDURE sp_DangKyVeThang
    @MaKH INT, @MaXe INT, @SoThang INT, @MaNV INT,
    @PhuongThuc NVARCHAR(20) = N'Tiền mặt'
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;
    BEGIN TRAN;

    DECLARE @GiaThang DECIMAL(12,0) = (
        SELECT TOP 1 b.DonGia FROM BangGia b JOIN Xe x ON x.MaLoai = b.MaLoai
        WHERE x.MaXe = @MaXe AND b.HinhThuc = N'Tháng'
          AND CAST(GETDATE() AS DATE) >= b.NgayApDung
          AND (b.NgayKetThuc IS NULL OR CAST(GETDATE() AS DATE) <= b.NgayKetThuc)
        ORDER BY b.NgayApDung DESC);
    IF @GiaThang IS NULL
    BEGIN
        ROLLBACK; THROW 50004, N'Chưa có bảng giá tháng cho loại xe này.', 1;
    END

    DECLARE @Tong DECIMAL(12,0) = @GiaThang * @SoThang;
    DECLARE @BatDau DATE = CAST(GETDATE() AS DATE);

    INSERT INTO VeThang(MaKH, MaXe, NgayBatDau, NgayHetHan, GiaVe)
    VALUES (@MaKH, @MaXe, @BatDau, DATEADD(MONTH, @SoThang, @BatDau), @Tong);
    DECLARE @MaVe INT = SCOPE_IDENTITY();

    INSERT INTO ThanhToan(MaVe, SoTien, PhuongThuc, MaNV) VALUES (@MaVe, @Tong, @PhuongThuc, @MaNV);
    COMMIT;

    SELECT @MaVe AS MaVe, @Tong AS TongTien;
END
GO


CREATE OR ALTER PROCEDURE sp_CapNhatVeHetHan
AS
    UPDATE VeThang SET TrangThai = N'Hết hạn'
    WHERE TrangThai = N'Còn hạn' AND NgayHetHan < CAST(GETDATE() AS DATE);
GO




CREATE OR ALTER VIEW vw_XeDangTrongBai AS
SELECT l.MaLuot, x.BienSo, lx.TenLoai, v.TenViTri, v.KhuVuc, l.GioVao,
       DATEDIFF(MINUTE, l.GioVao, GETDATE()) AS SoPhutDaGui,
       CASE WHEN l.MaVe IS NULL THEN N'Vé lượt' ELSE N'Vé tháng' END AS LoaiVe
FROM LuotGui l
JOIN Xe x ON x.MaXe = l.MaXe
JOIN LoaiXe lx ON lx.MaLoai = x.MaLoai
JOIN ViTri v ON v.MaViTri = l.MaViTri
WHERE l.TrangThai = N'Đang gửi';
GO


CREATE OR ALTER VIEW vw_ChoTrong AS
SELECT v.KhuVuc, lx.TenLoai,
       COUNT(*) AS TongCho,
       SUM(CASE WHEN v.TrangThai = N'Trống' THEN 1 ELSE 0 END) AS ChoTrong
FROM ViTri v JOIN LoaiXe lx ON lx.MaLoai = v.MaLoai
GROUP BY v.KhuVuc, lx.TenLoai;
GO

CREATE OR ALTER VIEW vw_DoanhThuNgay AS
SELECT CAST(ThoiGian AS DATE) AS Ngay,
       SUM(SoTien) AS TongDoanhThu,
       SUM(CASE WHEN MaLuot IS NOT NULL THEN SoTien ELSE 0 END) AS DoanhThuLuot,
       SUM(CASE WHEN MaVe   IS NOT NULL THEN SoTien ELSE 0 END) AS DoanhThuVeThang
FROM ThanhToan
GROUP BY CAST(ThoiGian AS DATE);
GO

CREATE OR ALTER VIEW vw_VeSapHetHan AS
SELECT vt.MaVe, kh.HoTen, kh.SDT, x.BienSo, vt.NgayHetHan,
       DATEDIFF(DAY, GETDATE(), vt.NgayHetHan) AS ConLaiNgay
FROM VeThang vt
JOIN KhachHang kh ON kh.MaKH = vt.MaKH
JOIN Xe x ON x.MaXe = vt.MaXe
WHERE vt.TrangThai = N'Còn hạn'
  AND vt.NgayHetHan BETWEEN CAST(GETDATE() AS DATE) AND DATEADD(DAY, 7, CAST(GETDATE() AS DATE));
GO

