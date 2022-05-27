Learn Haskell 
=============

## Install Haskell (using ghcup) - Preferred

[ghcup](https://www.haskell.org/ghcup/) is a tool that can be used to manage the Haskell toolchain: -
* [ghc](https://www.haskell.org/ghc/) - The Glasgow Haskell compiler.
* [cabal](https://cabal.readthedocs.io/en/latest/) - Package system for Haskell software.
* [Haskell language server](https://haskell-language-server.readthedocs.io/en/latest/) - LSP implementation for Haskell.
* [Stack](https://docs.haskellstack.org/en/latest/README/) - Haskell tool stack manager.

It installs everything into a local directory `.ghcup`, so doe not require root access (unlike stack below).

## Install Haskell (using stack) - Alternative

Haskell can be installed via [Stack](https://docs.haskellstack.org/en/stable/README/).

1. First install stack itself by running: -

```bash
    curl -sSL https://get.haskellstack.org/ | sh
```

1. And then run GHCI using `stack ghci`.

More details on stack can be found [here](Stack.md)

