# GreenVsRed
## Author: Nedko Nikolov / nnikolov ##
### Version: 1.0.0 ###

A game that determines how many times a Cell within a Grid, containing a rectangular cell matrix, has a green color over the progression of the different generations of the Grids cell matrix, up to a certain targeted Grid generation.

Some naming conventions and the corresponding terminology:

      - cellMatrix: A 2D List<List<Cell>> that contains Cells with a specific color. A generic cellMatrix may not
        have a rectangular shape.
      
          - rectangular cellMatrix: A cellMatrix that has a rectangular shape when presented on a 2D plane.

      - Grid: A cellMatrix that conforms to the shape constraint:
        cellMatrixAxisSizeHorizontal <= cellMatrixAxisSizeVertical < 1000

A description of the Grid generation progression algorithm:

>**NOTE: The neighbours of a Cell also include the ones that are diagonal to it, therefore each Cell can have up to 8 neighbours.**

      - If a red color Cell is surrounded by 3 or 6 green Cells, it will become green in the next generation.
      - If a red color Cell is surrounded by 1, 2, 4, 5, 7 or 8 green Cells, it will remain red in the next generation.
      - If a green color Cell is surrounded by 1, 4, 5, 7 or 8 green Cells, it will become red in the next generation.
      - If a green color Cell is surrounded by 2, 3 or 6 green Cells, it will remain green in the next generation.

Expected inputs from an user:

      (1) A String that conforms to the pattern: X, Y; Where X & Y are numbers that can be stored in a 'short'
          primitive type. X & Y respectively represent the horizontal and vertical sizes of the intended cell matrix,
          which will be stored in a Grid.

      (2) For the next Z lines (where Z is equal to the vertical size of the cell matrix defined in input step (1)):

         - A String that conforms to the pattern: XXX...; Where each X is a number that can be stored in a 'short'
          primitive type. The amount of X numbers corresponds to the horizontal size of the cell matrix defined in
          input step (1). These numbers represent the intended colors of the Cells in the cell matrix that will be
          stored in a Grid.

      (3) A String that conforms to the pattern: X, Y, N; Where X & Y are numbers that can be stored in a 'short'
          primitive type. N is a number that can be stored in a 'long' primitive type. X & Y respectively represent the
          horizontal and vertical coordinates of a Cell in the defined cell matrix (via input steps (1) & (2)) that will
          have its color tracked over N generations.
          
**The pre-compiled binary file of version: 1.0.0 can be found [here](https://github.com/n-nikol98/GreenVsRed/releases).**
