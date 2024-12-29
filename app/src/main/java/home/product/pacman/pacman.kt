package home.product.pacman

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.random.Random
import kotlin.system.exitProcess


const val WIDTH = 40
const val HEIGHT = 20
const val PACMAN = 'C'
const val WALL = '#'
const val FOOD = '.'
const val EMPTY = ' '
const val DEMON = 'X'
var game_state=true
var res = 0
var score = 0
var pacman_x = 0
var pacman_y = 0

val board: Array<Array<Char>> = Array(HEIGHT) { Array(WIDTH) { ' ' } }
var food = 0
var food_relative = 0
var demon_relative=0
var curr = 0;

fun initialize(){
    val random= Random
    for (i in 0 until HEIGHT) {
        for (j in 0 until WIDTH) {
            if (i == 0 || j == WIDTH - 1 || j == 0 || i == HEIGHT - 1) {
                board[i][j] = WALL
            } else board[i][j] = EMPTY
        }
    }
    var count = 50
    while (count != 0) {
        val i = (random.nextInt(HEIGHT ))
        val j = (random.nextInt(WIDTH ))

        if (board[i][j] !== WALL && board[i][j] !== PACMAN) {
            board[i][j] = WALL
            count--
        }
    }
    var num = 5
    while (num>0) {
        val row: Int = (random.nextInt(HEIGHT ))
        for (j in 3 until WIDTH - 3) {
            if (board[row][j] !== WALL
                && board[row][j] !== PACMAN
            ) {
                board[row][j] = WALL
            }
        }
        num--
    }
    count = 10
    while (count != 0) {
        val i: Int = (random.nextInt(HEIGHT ))
        val j: Int = (random.nextInt(HEIGHT ))

        if (board[i][j] !== WALL && board[i][j] !== PACMAN) {
            board[i][j] = DEMON
            count--
        }
    }
    pacman_x = WIDTH / 2;
    pacman_y = HEIGHT / 2;
    board[pacman_y][pacman_x] = PACMAN;

    for (i in 0 until HEIGHT) {
        for (j in 0 until WIDTH) {
            if (i % 2 == 0 && j % 2 == 0 &&
                board[i][j] !== WALL && board[i][j] !== DEMON
                && board[i][j] !== PACMAN) {
                board[i][j] = FOOD
                food++
            }
        }
    }
}
fun draw(){
    for (i in 0 until HEIGHT) {
        for (j in 0 until WIDTH) {
            print( "${board[i][j]}")
        }
        println()
    }
    println("Очки: $score")
}
fun move(move_x:Int,move_y:Int) {
    val x = pacman_x + move_x;
    val y = pacman_y + move_y;

    if (board[y][x] != WALL) {
        if (board[y][x] == FOOD) {
            score+=1;
           food-=1
            food_relative += 1
            curr++;
            if (food_relative==20 ) {
                res=1;
                return;
            }
        } else if (board[y][x] == DEMON) {
            demon_relative+=1
            if (demon_relative==3) {
                res=2;
                return;
            }

        }

        board[pacman_y][pacman_x] = EMPTY;
        pacman_x = x;
        pacman_y = y;
        board[pacman_y][pacman_x] = PACMAN;
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun clearConsole() {
    try {
        val process = ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
        process.waitFor()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    initialize();
    var ch = " "
 //   food -= 35;
  //  var totalFood = food;

    println(" Используйте клавиши w(вверх), a(влево) , d(вправо) , s(вниз), q(выход)");

    print("Нажмите Y для старта : \n");

      ch = readln();
     if (ch[0] == 'Y' && ch[0] =='y') {
        println("Старт игры")
    }
        else if (ch[0] == 'Q' && ch[0] =='q'||ch[0] != 'Y' && ch[0] !='y'){
            print("Выход из игры! ")
        exitProcess(1)
    }


while(game_state){
    //clearConsole()
    draw()
    println("Количество еды: $food")
    print("Сьедено: $curr ")
    if (res ==2) {
       //clearConsole()
        print("Игра окончена! Призраки устранены Ваши очки: $score")
        exitProcess(1)
    }

    if (res == 1) {
        //clearConsole()
        print("Вы победили! Ваши очки : $score")
        exitProcess(1)
          }
    ch = readln();
    when (ch[0].toChar()) {
        'w' -> move(0, -1)
        's' -> move(0, 1)
        'a' -> move(-1, 0)
        'd' -> move(1, 0)
        'q' -> {
            print("Игра окончена! Ваши очки: $score")
           game_state=false
        }
    }
}

}
