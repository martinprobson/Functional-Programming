
# [The Haskell Tool Stack](https://docs.haskellstack.org/en/stable/README/) - Notes

## Notes
`stack` is built on top of : -
* The [Glasgow Haskell compiler](https://www.haskell.org/ghc/). Stack manages GHC installations and select the appropriate compiler version for your project.
* The [Cabel build system](https://www.haskell.org/cabal/)
* The [Haskage package repository](https://hackage.haskell.org/)
* The [Stackage package collection](https://www.stackage.org/)

## Start a new project

```bash
stack new my-project
cd my-project
stack setup
stack build
stack exec my-project-exec
```

## Add a dependency
Add the package name to the `package.yaml` file in the `dependencies` section and run `stack build` again.


