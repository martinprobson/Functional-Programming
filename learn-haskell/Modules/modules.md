# Modules

A Haskell module is a collection of related functions, types and type classes. A Haskell program is a collection of
modules where the main module loads up the other modules and uses the functions defined in them to do something.

The Haskell standard library is split up into modules. All the functions, types and type classes we have dealt with
so far are in the [Prelude](https://hackage.haskell.org/package/base-4.14.1.0/docs/Prelude.html) module, which is loaded by default. 

The syntax for importing modules in a Haskell script is `import <module name>`. This must be done before importing
any functions, so imports are usually done at the top of the file.

You can also put the functions of modules into the global namespace when using GHCI: -

```
:m + Data.List
*Main Data.List
```

You can selectively import functions from a module:-

```
import Data.List (nub, sort)
```

Or import all functions except a few select ones: -

```
import Data.List hiding (nub ,sort)
```

Another way of dealing with name clashes is to do qualified imports. 

```
import qualified Data.Map as M
```

we can then refer to `Data.Map`s filter function using `M.filter`.

## Standard Libraries
You can use [this reference](https://downloads.haskell.org/~ghc/latest/docs/html/libraries/) to see what modules are in the standard library.

To search for functions or to find out where they are located, use [Hoogle](https://hoogle.haskell.org/), the Haskell search engine.

| Module    | Description                      |
| Data.List | Functions dealing with Lists     |
| Data.Char | Functions dealing with Chars     |
| Data.Map  | Functions dealing with k/v pairs |
| Data.Set  | Functions dealing with Sets      |

## Making our own Modules

See [Modules](https://www.haskell.org/tutorial/modules.html)
