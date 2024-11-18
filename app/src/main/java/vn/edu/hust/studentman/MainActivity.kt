package vn.edu.hust.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students, ::onEditStudent, ::onRemoveStudent)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showAddStudentDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Thêm sinh viên")
      .setView(dialogView)
      .setPositiveButton("Thêm") { _, _ ->
        val name = dialogView.findViewById<android.widget.EditText>(R.id.edit_student_name).text.toString()
        val id = dialogView.findViewById<android.widget.EditText>(R.id.edit_student_id).text.toString()
        if (name.isNotBlank() && id.isNotBlank()) {
          students.add(StudentModel(name, id))
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
      .setNegativeButton("Hủy", null)
      .create()
    dialog.show()
  }

  private fun onEditStudent(position: Int) {
    val student = students[position]
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null).apply {
      findViewById<android.widget.EditText>(R.id.edit_student_name).setText(student.studentName)
      findViewById<android.widget.EditText>(R.id.edit_student_id).setText(student.studentId)
    }
    AlertDialog.Builder(this)
      .setTitle("Chỉnh sửa sinh viên")
      .setView(dialogView)
      .setPositiveButton("Cập nhật") { _, _ ->
        val name = dialogView.findViewById<android.widget.EditText>(R.id.edit_student_name).text.toString()
        val id = dialogView.findViewById<android.widget.EditText>(R.id.edit_student_id).text.toString()
        if (name.isNotBlank() && id.isNotBlank()) {
          students[position] = StudentModel(name, id)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Hủy", null)
      .create()
      .show()
  }

  private fun onRemoveStudent(position: Int) {
    val studentToRemove = students[position]

    AlertDialog.Builder(this)
      .setTitle("Xác nhận xóa")
      .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${studentToRemove.studentName} không?")
      .setPositiveButton("Xóa") { _, _ ->
        // Thực hiện xóa sinh viên
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)

        // Hiển thị Snackbar với tùy chọn Undo
        Snackbar.make(findViewById(R.id.main), "Đã xóa ${studentToRemove.studentName}", Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            students.add(position, studentToRemove)
            studentAdapter.notifyItemInserted(position)
          }
          .show()
      }
      .setNegativeButton("Hủy", null) // Người dùng chọn Hủy thì không làm gì
      .create()
      .show()
  }


}