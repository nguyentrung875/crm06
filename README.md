# Dự án Website CRM - Java Servlet

Xây dựng hệ thống CRM quản lý công việc nhân viên công ty với các yêu cầu sau:

- Hệ thống cho phép quản trị hệ thống (ADMIN) đăng nhập và thêm mới, sửa, xóa, xem thông tin, cấp quyền cho nhân viên.
- Hệ thống đảm bảo cho “quản lý dự án” (LEADER) có thể dễ dàng đăng nhập thêm mới, sửa, xóa, xem thông tin dự án. Đồng thời có thể thêm nhân viên vào dự án và phân công công việc cho từng nhân viên thuộc dự án.
- Hệ thống cũng cho phép “quản lý dự án” có thể theo dõi các thống kê về tiến độ công việc của từng nhân viên trong mỗi dự án.
- Hệ thống cho phép nhân viên đăng nhập với tư cách thành viên, cập nhật tiến độ công việc. Xem các thống kê về tiến độ của các việc đã và đang thực hiện.
- Hệ thống cũng cho phép quản trị hệ thống xem các thống kê về tiến độ các dự án.

Demo: https://www.youtube.com/watch?v=NmkM-DXyTXw

# Công nghệ sử dụng
- Java Servlet
- MySQL
- HTML
- CSS
- JQuery

# HDSD

## Tạo MySQL database
- Chạy file crmapp.sql

## Cấu hình database trong servlet

Mở file \src\main\java\crm06\config\MySQLConfig.java. 
Cấu hình các trường sau theo MySQL trên máy bạn.
- url  =  "jdbc:mysql://localhost:[database-port]/crmapp"
- username  =  "[username]"
- password  =  "[password]"

## Khởi động dự án
- Truy cập đường dẫn: http://localhost:8080/crm06

## Đăng nhập hệ thống

- Tài khoản: admin
- Mật khẩu: admin

