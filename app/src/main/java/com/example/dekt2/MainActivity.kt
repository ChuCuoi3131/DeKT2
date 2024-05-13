package com.example.dekt2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.dekt2.ui.theme.DeKT2Theme
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SnapshotListenOptions

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeKT2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var DSsp = mutableStateListOf<Sanpham?>()
                    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
                    db.collection("San Pham").get().addOnSuccessListener { queryDocumentSnapshots ->
                        if (!queryDocumentSnapshots.isEmpty) {
                            val list = queryDocumentSnapshots.documents
                            for (d in list) {
                                val c: Sanpham? = d.toObject(Sanpham::class.java)
                                c?.IDsp = d.id
                                Log.e("AAA", "ID san pham la: " + c!!.IDsp)
                                DSsp.add(c)
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Khong tim thay du lieu",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@MainActivity,
                            " khong lay duoc du lieu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                    Content(LocalContext.current, DSsp)
                }
            }
        }
    }
}


@Composable
fun Content(context: Context, DSsp: SnapshotStateList<Sanpham?>) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        var (col1, col2) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(col1) {
                    top.linkTo(parent.top, margin = 10.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var tensp by remember {
                mutableStateOf("")
            }
            var loaisp by remember {
                mutableStateOf("")
            }
            var giasp by remember {
                mutableStateOf("")
            }
            var urlsp by remember {
                mutableStateOf("")
            }

            Text(text = "Dữ liệu sản phẩm", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
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
                modifier = Modifier.width(350.dp),
                singleLine = true
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
                modifier = Modifier.width(350.dp),
                singleLine = true
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
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = urlsp,
                onValueChange = {
                    urlsp = it
                },
                label = {
                    Text(text = "URL sản phẩm")
                },
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                            contentDescription = null
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue
                ),
                modifier = Modifier.width(350.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /*TODO*/
                    if (TextUtils.isEmpty(tensp)) {
                        Toast.makeText(context, "Nhap ten sp", Toast.LENGTH_SHORT).show()
                    } else if (TextUtils.isEmpty(loaisp)) {
                        Toast.makeText(context, "Nhap loai sp", Toast.LENGTH_SHORT).show()
                    } else if (TextUtils.isEmpty(giasp)) {
                        Toast.makeText(context, "Nhap gia sp", Toast.LENGTH_SHORT).show()
                    } else {
                        addDataToFirebase("", tensp, loaisp, giasp.toInt(), urlsp, context)
                    }
                },
                modifier = Modifier.width(350.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff6495ed),
                    contentColor = Color.White
                )
            ) {
                Text(text = "THÊM SẢN PHẨM", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(col2) {
                    top.linkTo(col1.bottom, margin = 10.dp)
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Danh sách sản phẩm", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(modifier = Modifier.width(350.dp)) {
                itemsIndexed(DSsp) { index, item ->
                    Row(
                        modifier = Modifier.border(BorderStroke(1.dp, Color.Blue)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Image(
                            painter = rememberAsyncImagePainter(item?.urlsp),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp, 100.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Tên sp: ${item?.tensp}", fontSize = 13.sp)
                            Text(text = "Giá sp: ${item?.giasp}", fontSize = 13.sp)
                            Text(text = "Loại sp: ${item?.loaisp}", fontSize = 13.sp)
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            IconButton(onClick = {
                                val i = Intent(context, UpdateSanpham::class.java)
                                i.putExtra("IDsp", item?.IDsp)
                                i.putExtra("tensp", item?.tensp)
                                i.putExtra("loaisp", item?.loaisp)
                                i.putExtra("giasp", item?.giasp)
                                i.putExtra("urlsp", item?.urlsp)
                                context.startActivity(i)
                            }, modifier = Modifier.border(BorderStroke(1.dp, Color.Yellow))) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                            }
                            IconButton(onClick = {
                                item?.IDsp?.let { deleteDataFromFirebase(it, context) }
                            }, modifier = Modifier.border(BorderStroke(1.dp, Color.Red))) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun addDataToFirebase(
    IDsp: String,
    tensp: String,
    loaisp: String,
    giasp: Int,
    urlsp: String?,
    context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbSp: CollectionReference = db.collection("San Pham")
    val sp = Sanpham(IDsp, tensp, loaisp, giasp, urlsp)

    dbSp.add(sp).addOnSuccessListener {
        Toast.makeText(context, "Da them san pham vao Firebase", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener { e ->
        Toast.makeText(context, "Them that bai $e", Toast.LENGTH_SHORT).show()
    }
}

fun deleteDataFromFirebase(IDsp: String, context: Context) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    db.collection("San Pham").document(IDsp).delete().addOnSuccessListener {
        // displaying toast message when our course is deleted.
        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener {
        Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    DeKT2Theme {
        lateinit var DSsp: SnapshotStateList<Sanpham?>
        Content(LocalContext.current, DSsp)
    }
}