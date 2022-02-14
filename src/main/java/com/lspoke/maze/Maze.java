package com.lspoke.maze;

/*
Написать программу, в которой бы в main создавалась карта лабиринта. Например:
X11111
01010Y
000101
011101
001101
100001
111111
Где X – стартовая точка, а Y – куда мы должны прийти.
Программа должна найти любой (пусть не оптимальный), но маршрут до выхода и распечатать его на экран.
*/

import java.util.Stack;

public class Maze {
    public static void main(String[] args) {
        int line = 7;
        int column = 6;

        String[][] maze = {
                {"1","1","1","1","1","1"},
                {"X","1","0","0","0","1"},
                {"0","0","0","1","0","1"},
                {"0","1","1","1","0","Y"},
                {"0","0","1","1","1","1"},
                {"1","0","0","0","0","1"},
                {"1","1","1","1","1","1"}
        };
        for(int i = 0; i< line; i++) {
            for (int j = 0; j < column; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        findWay(maze, line, column);
        System.out.println("Exit from maze");
        for(int i = 0; i< line; i++) {
            for (int j = 0; j < column; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }

    }
    //The function of finding a way out of the maze
    public static void findWay(String[][] maze, int line, int column){
        Stack<Point> stack = new Stack<>();
        //Find start points
        StartPoints start = findStartPoint(maze, line, column);
        if(start != null) {
            int i = start.getFirst();
            int j = start.getSecond();

            String[][] tmpMaze = new String[line][column];
            copyMaze(maze, tmpMaze, line, column);

            //First node
            Point point = new Point(null, i, j);
            stack.push(point);

            //Pass through all points. Search exit
            while (!stack.isEmpty()) {
                Point tmpPoint = stack.pop();
                if (tmpPoint.j + 1 < column) {
                    if (!tmpMaze[tmpPoint.i][tmpPoint.j + 1].equals("")) {
                        if (tmpMaze[tmpPoint.i][tmpPoint.j + 1].equals("0")) {
                            Point newPoint = new Point(tmpPoint, tmpPoint.i, tmpPoint.j + 1);
                            stack.push(newPoint);
                        } else if (tmpMaze[tmpPoint.i][tmpPoint.j + 1].equals("Y")) {
                            Point newTree = new Point(tmpPoint, tmpPoint.i, tmpPoint.j + 1);
                            while (!stack.isEmpty()) {
                                stack.pop();
                            }
                            stack.push(newTree);
                            break;
                        }
                    }
                }
                if (tmpPoint.i + 1 < line) {
                    if (!tmpMaze[tmpPoint.i + 1][tmpPoint.j].equals("")) {
                        if (tmpMaze[tmpPoint.i + 1][tmpPoint.j].equals("0")) {
                            Point newPoint = new Point(tmpPoint, tmpPoint.i + 1, tmpPoint.j);
                            stack.push(newPoint);
                        } else if (tmpMaze[tmpPoint.i + 1][tmpPoint.j].equals("Y")) {
                            Point newTree = new Point(tmpPoint, tmpPoint.i + 1, tmpPoint.j);
                            while (!stack.isEmpty()) {
                                stack.pop();
                            }
                            stack.push(newTree);
                            break;
                        }
                    }
                }
                if (tmpPoint.j - 1 >= 0) {
                    if (!tmpMaze[tmpPoint.i][tmpPoint.j - 1].equals("")) {
                        if (tmpMaze[tmpPoint.i][tmpPoint.j - 1].equals("0")) {
                            Point newPoint = new Point(tmpPoint, tmpPoint.i, tmpPoint.j - 1);
                            stack.push(newPoint);
                        } else if (tmpMaze[tmpPoint.i][tmpPoint.j - 1].equals("Y")) {
                            Point newPoint = new Point(tmpPoint, tmpPoint.i, tmpPoint.j - 1);
                            while (!stack.isEmpty()) {
                                stack.pop();
                            }
                            stack.push(newPoint);
                            break;
                        }
                    }
                }
                if (tmpPoint.i - 1 >= 0) {
                    if (!tmpMaze[tmpPoint.i - 1][tmpPoint.j].equals("")) {
                        if (tmpMaze[tmpPoint.i - 1][tmpPoint.j].equals("0")) {
                            Point newPoint = new Point(tmpPoint, tmpPoint.i - 1, tmpPoint.j);
                            stack.push(newPoint);
                        } else if (tmpMaze[tmpPoint.i - 1][tmpPoint.j].equals("Y")) {
                            Point newTree = new Point(tmpPoint, tmpPoint.i - 1, tmpPoint.j);
                            while (!stack.isEmpty()) {
                                stack.pop();
                            }
                            stack.push(newTree);
                            break;
                        }
                    }
                }
                tmpMaze[tmpPoint.i][tmpPoint.j] = "";
            }

            Point tr = stack.pop();
            // Exit route
            while (tr.previous != null) {
                if (!maze[tr.i][tr.j].equals("Y")) {
                    maze[tr.i][tr.j] = "+";
                }
                tr = tr.previous;
            }
        }else{
            System.out.println("Start point is not find");
        }
    }
    public static void copyMaze(String[][] original, String[][] copy, int line, int column){
        for(int i = 0; i < line; i++){
            if (column >= 0) System.arraycopy(original[i], 0, copy[i], 0, column);
        }
    }
    //Find start points
    public static StartPoints findStartPoint(String[][] maze, int line, int column){
        for(int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if(maze[i][j].equals("X")) return  new StartPoints(i, j);
            }
        }
        return null;
    }

    static public class Point{
        Point previous;
        int i;
        int j;

        public Point(Point previous, int i, int j) {
            this.previous = previous;
            this.i = i;
            this.j = j;
        }
    }

    static public class StartPoints{
        private int first;
        private int second;

        public StartPoints(int first, int second) {
            this.first = first;
            this.second = second;
        }
        public int getFirst() {
            return first;
        }
        public int getSecond() {
            return second;
        }
    }
}
