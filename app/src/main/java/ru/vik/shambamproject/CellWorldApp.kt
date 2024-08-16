package ru.vik.shambamproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CellWorldApp(){
    var cells by remember { mutableStateOf(listOf<Cell>()) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF4B0082), Color.Black)
            )
        )
    ){
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = {
                BottomAppBar(
                    containerColor = Color.Transparent
                ) {
                    Button(
                        onClick = {
                            cells = addNewCell(cells)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(text = "Сотворить", fontSize = 18.sp)
                    }
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent, // Устанавливаем фоновый цвет на прозрачный
                        titleContentColor = Color.White // Цвет заголовка
                    ),
                    title = {
                        Text(
                            text = "Клеточное наполнение", color = Color.White
                        )
                    }
                )
            },
            content = { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    items(cells.size){ index ->
                        CellTile(cell = cells[index])
                    }
                }
            }
        )
    }
}

@Composable
fun CellTile(cell: Cell) {
    val icon = when (cell) {
        Cell.Alive, Cell.EarlyBorn -> painterResource(id = R.drawable.ic_alive_cell_foreground) // замените на вашу иконку для живой клетки
        Cell.Dead, Cell.EarlyDead -> painterResource(id = R.drawable.ic_dead)   // замените на вашу иконку для мертвой клетки
    }

    val additionalText = when (cell) {
        Cell.Alive -> "Эта клетка наполнена жизнью."
        Cell.Dead -> "Эта клетка мертва."
        Cell.EarlyBorn -> "Эта клетка родилась со всеми!"
        Cell.EarlyDead -> "Эта клетка умерла со всеми"
    }

    val backgroundGradient = when (cell){
        Cell.Alive, Cell.EarlyBorn -> Brush.verticalGradient(
            colors = listOf(Color(0xFFFFFF00), Color(0xFFFFDB58))
        )
        Cell.Dead, Cell.EarlyDead -> Brush.verticalGradient(
            colors = listOf(Color(0xFF50C878), Color(0xFF00A86B))
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(backgroundGradient)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = cell.toString(), fontSize = 20.sp, color = Color.Black)
            Text(text = additionalText, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

fun addNewCell(cells: List<Cell>) : List<Cell>{
    val newCell = if (Random.nextBoolean()) Cell.Alive else Cell.Dead
    val updateCells = cells + newCell

    if (updateCells.size >= 3){
        val lastThreeCells = updateCells.takeLast(3)
        if (lastThreeCells.all { it is Cell.Alive }){
            return updateCells + Cell.EarlyBorn
        }
        else if (lastThreeCells.all { it is Cell.Dead }){
            return updateCells + Cell.EarlyDead
        }
    }

    return updateCells
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CellWorldApp()
}