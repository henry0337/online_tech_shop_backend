package dev.quochung2003.techonlineshopbackend.util

/**
 * Chuyển đổi chuỗi cho sẵn thành một lệnh có thể dùng trong CLI (giao diện dòng lệnh) được tạo bởi [ProcessBuilder], nếu có thể.
 * @return Danh sách chuỗi con sau khi đã tách để có thể sử dụng với [ProcessBuilder].
 */
fun String.toProcessBuilderCommand(): List<String> =
    this.split(" ").filter { it.isNotBlank() }