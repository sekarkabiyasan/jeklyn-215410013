package id.utdi.kucingku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                    ArtGalleryApp()
                }
            }
        }
    }
}

@Composable
fun ArtGalleryApp() {
    val artPieces = remember { generateArtPieces() }
    var selectedArtPiece by remember { mutableStateOf(artPieces[0]) }
    var currentIndex by remember { mutableStateOf(0) }
    var isDetailsVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tampilkan gambar dan judul seni tanpa deskripsi
        ArtPieceWithoutDescription(selectedArtPiece)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    currentIndex = (currentIndex - 1 + artPieces.size) % artPieces.size
                    selectedArtPiece = artPieces[currentIndex]
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Previous Art",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    currentIndex = (currentIndex + 1) % artPieces.size
                    selectedArtPiece = artPieces[currentIndex]
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Next Art",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Ketika tombol "View Details" diklik, tampilkan deskripsi seni
                isDetailsVisible = true
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "View Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Tampilkan deskripsi seni jika isDetailsVisible true
        if (isDetailsVisible) {
            ArtPieceWithDescription(selectedArtPiece)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tampilkan daftar seni
        ArtPieceList(artPieces)
    }
}

@Composable
fun ArtPieceWithoutDescription(artPiece: ArtPiece) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
    ) {
        Text(
            text = artPiece.name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = artPiece.imageResId),
            contentDescription = artPiece.name,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )
    }
}

@Composable
fun ArtPieceWithDescription(artPiece: ArtPiece) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
    ) {
        Text(
            text = artPiece.name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tampilkan deskripsi seni dalam LazyColumn yang dapat di-scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp) // Atur tinggi sesuai kebutuhan
        ) {
            item {
                Text(
                    text = artPiece.description,
                    fontSize = 16.sp,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}

@Composable
fun ArtPieceList(artPieces: List<ArtPiece>) {
    LazyColumn {
        items(artPieces) { artPiece ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = artPiece.imageResId),
                    contentDescription = artPiece.name,
                    modifier = Modifier.size(60.dp)
                )

                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = artPiece.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF333333)
                    )

                    Text(
                        text = artPiece.description,
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                }
            }
        }
    }
}

data class ArtPiece(val id: Int, val name: String, val description: String, val imageResId: Int)

fun generateArtPieces(): List<ArtPiece> {
    return listOf(
        ArtPiece(1, "Jeger", "jenis kucing British Short Hair merupakan jenis kucing yang ramah dan suka bergaul. Meskipun terkadang tidak terlalu aktif dan pendiam, namun kucing ini memiliki sifat setia dan manis. Ini termasuk jenis kucing yang aman dipelihara bersama dengan hewan lainnya.", R.drawable.kucing1),
        ArtPiece(2, "Jeklyn", "jenis kucing Lokal/Domestik  adalah kucing yang ada di seluruh belahan dunia kecuali Antartika. Mereka dapat bertahan hidup di mana saja. Mulai dari padang pasir, gunung, dan perkotaan. Kucing domestik memiliki kemampuan untuk mengalahkan predator yang memiliki ukuran yang sama dengan tubuhnya.", R.drawable.kucing2),
        ArtPiece(3, "Jacko", "jenis kucing Anggora Long Hair jenis kucing domestik yang berasal dari Turki sejak abad ke-17. Salah satu kucing tertua ini berasal dari wilayah Ankara di Turki, sehingga terkadang disebut juga Ankara. Hewan berbulu ini juga memiliki warna bulu dan mata yang khas dengan sifat yang energik dan cerdas.", R.drawable.kucing3),
        ArtPiece(4, "Jesi", "jenis kucing Mainecoon kucing berbadan besar dan tinggi dengan berat jantan sekitar 6–9 kg dan betina sekitar 4–6 kg. Tubuhnya dapat mencapai ketinggian hingga sekitar 1 meter. Maine coon merupakan kucing yang kuat dan berotot. Kucing ini memiliki pertumbuhan dewasa yang lama, yaitu sekitar 3–5 tahun.", R.drawable.kucing4),
        ArtPiece(5, "Jelyn", "jenis kucing Persia Long Hair pada umumnya memiliki bentuk tubuh yang gemuk, besar, dan tambun. Selain itu, yang menjadi khas juga dalam kucing persia ini adalah bentuk hidungnya yang pesek, wajahnya terlihat bulat, dan memiliki bulu yang panjang. Jika diamati dari samping, dahi, hidung, dan dagu terlihat sangat datar", R.drawable.kucing5),
    )
}
