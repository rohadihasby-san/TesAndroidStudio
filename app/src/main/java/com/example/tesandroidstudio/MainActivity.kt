package com.example.tesandroidstudio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tesandroidstudio.ui.theme.TesAndroidStudioTheme

class MainActivity : ComponentActivity() {
    // Tag konstan buat mempermudah pencarian di logcat Android Studio
    private val TAG = "LIFECYCLE_TAG"

    // State Compose buat nampilin status siklus hidup langsung di layar HP
    private var currentStatus by mutableStateOf("onCreate: Aplikasi Dimulai")
    private var resumeCount by mutableIntStateOf(0)

    // Dipanggil saat Activity pertama kali dibuat di memori RAM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate: Activity dibuat dan resource diinisialisasi")
        currentStatus = "Status: onCreate (Inisialisasi selesai)"

        setContent {
            TesAndroidStudioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IntentDemoScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // 2. Dipanggil saat Activity mulai kelihatan di layar HP
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Layar mulai kelihatan oleh user")
        currentStatus = "Status: onStart (Aplikasi Terlihat)"
    }

    // 3. Dipanggil saat Activity berada di titik terdepan dan bersiap menerima interaksi pengguna
    override fun onResume() {
        super.onResume()
        resumeCount++
        Log.d(TAG, "onResume: Aplikasi aktif dan interaktif (Fase ke-$resumeCount)")
        currentStatus = "Status: onResume (Aplikasi Siap Digunakan)"
        Toast.makeText(this, "Aplikasi Aktif (onResume)", Toast.LENGTH_SHORT).show()
    }

    // 4. Dipanggil saat Activity kehilangan sebagian besar fokus (misal ada pop up dan layar transisi)
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause: Aplikasi kehilangan sebagian besar fokus")
        currentStatus = "Status: onPause (Aplikasi Kehilangan Fokus)"
    }

    // 5. Dipanggil saat Activity sudah tertutup penuh atau user menekan tombol Home
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Aplikasi berada di latar belakang")
        currentStatus = "Status: onStop (Berada di Background)"
    }

    // 6. Dipanggil saat user membuka kembali aplikasi setelah menekan tombol Home
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Aplikasi dibuka kembali")
    }

    // 7. Dipanggil sebelum Activity dihapus secara total dari memori RAM
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity dihancurkan dari memory")
    }
}

@Composable
fun LifecycleScreen(
    status: String,
    counter: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card Informasi Status Siklus Hidup
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Status Siklus Hidup Activity",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = status,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Jumlah onResume: $counter",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        UILayoutDemoScreen(modifier = Modifier.weight(1f))
    }
}

@Composable
fun UILayoutDemoScreen(modifier: Modifier = Modifier) {
    var nameInput by remember { mutableStateOf("") }
    var submittedName by remember { mutableStateOf("") }
    
    // State buat scroll
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState), // Mencegah UI terpotong saat keyboard ditampilkan
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header Praktikum
        Text(
            text = "Pertemuan 3: Layout dan Komponen UI",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider()

        // 2. Demonstrasi Box dan Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Komponen Image di dalam Box
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo Android",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Komponen di dalam Box",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // 3. Demonstrasi Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BadgeCard(title = "Row 1", color = Color(0xFFE3F2FD))
            BadgeCard(title = "Row 2", color = Color(0xFFE8F5E9))
            BadgeCard(title = "Row 3", color = Color(0xFFFFF3E0))
        }

        // 4. Form Input dan Button
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Latihan Input Data", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Masukkan Nama Mahasiswa") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { submittedName = nameInput },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Tampilkan")
                }

                if (submittedName.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Halo, $submittedName! Selamat Belajar Compose.",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun BadgeCard(title: String, color: Color) {
    Surface(
        color = color,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LifecycleScreenPreview() {
    TesAndroidStudioTheme {
        LifecycleScreen(
            status = "Status: onResume (Aplikasi Siap Digunakan)",
            counter = 1
        )
    }
}

@Composable fun IntentDemoScreen(modifier: Modifier = Modifier) {
    // LocalContext diperlukan untuk menjalankan Intent di dalam Compose
    val context = LocalContext.current
    var nameInput by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Pertemuan 4: Intent & Navigasi",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider()

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Masukkan Nama Anda") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Explicit Intent
        Button (
            onClick = {
                // Mengirim data antar activity menggunakan key value pair
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("EXTRA_NAME", nameInput) // Tipe data string
                    putExtra("EXTRA_SEMESTER", 5) // Tipe data integer
                    putExtra("EXTRA_IS_ACTIVE", true) // Tipe data boolean
                }
                context.startActivity(intent)
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Hubungi Dosen (Implicit Intent)")
        }
    }
}

@Preview(showBackground = true, name = "Main Screen Preview")
@Composable fun IntentDemoScreenPreview() {
    TesAndroidStudioTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            IntentDemoScreen()
        }
    }
}