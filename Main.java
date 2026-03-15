import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	public static Connection layKetNoi() {
		try {
			String url = "jdbc:mysql://localhost:3306/QuanLySinhVien?useSSL=false";
			return DriverManager.getConnection(url, "root", "");
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n--- QUAN LY SINH VIEN ---");
			System.out.println("1. In tat ca sinh vien");
			System.out.println("2. In theo Lop sinh hoat");
			System.out.println("3. In theo Nganh (CNTT/KTPM)");
			System.out.println("4. Sap xep theo Diem TB");
			System.out.println("5. Liet ke theo Thang sinh");
			System.out.println("0. Thoat");
			System.out.print("Chon: ");
			int chon = Integer.parseInt(sc.nextLine());
			if (chon == 0)
				break;
			switch (chon) {
			case 1:
				hienThi("SELECT * FROM sinh_vien", "DANH SACH TAT CA");
				break;
			case 2:
				System.out.print("Nhap ten lop: ");
				String lop = sc.nextLine();
				hienThi("SELECT * FROM sinh_vien WHERE lop_sh = '" + lop + "'", "DS LOP " + lop);
				break;
			case 3:
				System.out.print("Nhap nganh: ");
				String ng = sc.nextLine();
				hienThi("SELECT * FROM sinh_vien WHERE nganh = '" + ng + "'", "DS NGANH " + ng);
				break;
			case 4:
				hienThi("SELECT * FROM sinh_vien ORDER BY diem_tb DESC", "DS SAP XEP DIEM");
				break;
			case 5:
				System.out.print("Nhap thang sinh: ");
				int t = Integer.parseInt(sc.nextLine());
				hienThi("SELECT * FROM sinh_vien WHERE MONTH(ngay_sinh) = " + t, "DS THANG " + t);
				break;
			}
		}
	}

	public static void hienThi(String sql, String tieuDe) {
		Connection conn = layKetNoi();
		if (conn == null)
			return;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			System.out.println("\n--- " + tieuDe + " ---");
			System.out.printf("| %-12s | %-20s | %-12s | %-6s | %-6s | %-10s |\n", "Ma SV", "Ho Ten", "Ngay Sinh",
					"Nganh", "DTB", "Lop");
			while (rs.next()) {
				System.out.printf("| %-12s | %-20s | %-12s | %-6s | %-6.2f | %-10s |\n", rs.getString(1),
						rs.getString(2), rs.getDate(3), rs.getString(4), rs.getDouble(5), rs.getString(6));
			}
			conn.close();
		} catch (Exception e) {
			System.out.println("Loi: " + e.getMessage());
		}
	}
}