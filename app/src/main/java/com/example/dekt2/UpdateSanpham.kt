package com.example.dekt2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.dekt2.ui.theme.DeKT2Theme
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UpdateSanpham : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeKT2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UpdateSP(LocalContext.current,
                        intent.getStringExtra("IDsp")!!,
                        intent.getStringExtra("tensp")!!,
                        intent.getStringExtra("loaisp")!!,
                        intent.getIntExtra("giasp", 0),
                        intent.getStringExtra("urlsp")!!
                    )
                }
            }
        }
    }
}

@Composable
fun UpdateSP(context: Context, IDsp: String, tensp: String, loaisp: String, giasp: Int, urlsp: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var tensp by remember {
            mutableStateOf(tensp)
        }
        var loaisp by remember {
            mutableStateOf(loaisp)
        }
        var giasp by remember {
            mutableStateOf(giasp.toString())
        }
        var urlsp by remember {
            mutableStateOf(urlsp)
        }

        Text(text = "Chỉnh sửa sản phẩm", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = tensp,
            onValueChange = {
                tensp = it
            },
            label = {
                Text(text = "Tên sản phẩm")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Blue
            ),
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = loaisp,
            onValueChange = {
                loaisp = it
            },
            label = {
                Text(text = "Loại sản phẩm")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Blue
            ),
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = giasp,
            onValueChange = {
                giasp = it
            },
            label = {
                Text(text = "Giá sản phẩm")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Blue
            ),
            modifier = Modifier.width(350.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = urlsp!!,
            onValueChange = {
                urlsp = it
            },
            label = {
                Text(text = "URL sản phẩm")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Blue
            ),
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { /*TODO*/
                if (TextUtils.isEmpty(tensp))  {
                    Toast.makeText(context, "Nhap ten sp", Toast.LENGTH_SHORT).show()
                } else if(TextUtils.isEmpty(loaisp)){
                    Toast.makeText(context, "Nhap loai sp", Toast.LENGTH_SHORT).show()
                } else if(TextUtils.isEmpty(giasp)){
                    Toast.makeText(context, "Nhap gia sp", Toast.LENGTH_SHORT).show()
                } else {
                   updateDataToFirebase(IDsp,tensp,loaisp,giasp.toInt(),urlsp,context)
                }
            },
            modifier = Modifier.width(350.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff6495ed),
                contentColor = Color.White
            )
        ) {
            Text(text = "CHỈNH SỬA SẢN PHẨM", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

fun updateDataToFirebase(IDsp: String, tensp: String, loaisp: String, giasp: Int, urlsp: String?, context: Context){
    val sp = Sanpham(IDsp, tensp, loaisp, giasp, urlsp)
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    db.collection("San Pham").document(IDsp).set(sp)
        .addOnSuccessListener {
            Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, MainActivity::class.java))
        }.addOnFailureListener {
            Toast.makeText(context, "Cap nhat that bai", Toast.LENGTH_SHORT).show()
        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateSPPreview() {
    DeKT2Theme {
        UpdateSP(LocalContext.current, IDsp = "", tensp = "", loaisp = "", giasp = 0, urlsp = "")
    }
}