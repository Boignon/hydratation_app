# 💧 Hydratation — Application de suivi d'hydratation

Application Android de suivi d'hydratation quotidienne, développée avec **Jetpack Compose** et **Material 3**. L'objectif est simple : vous aider à atteindre votre objectif de **2 litres d'eau par jour** grâce à une interface intuitive, sombre et moderne aux accents turquoise.

---

## 📱 Aperçu

- ⭕ **Cercle de progression** animé qui se remplit à chaque ajout
- ➕ **Ajout rapide** : +250 ml (bouton principal), +100 ml, +500 ml, ou montant personnalisé
- 📊 **Historique du jour** : chaque prise d'eau horodatée
- 🎯 **Objectif 2L** : message de félicitations automatique à 2000 ml
- 🔄 **Réinitialisation** de la journée en un clic
- 🌑 **Thème sombre** "Dark Water" avec accents turquoise

---

## 🛠️ Technologies utilisées

| Composant | Rôle |
|---|---|
| **Kotlin 2.1.0** | Langage principal |
| **Jetpack Compose** | Interface utilisateur déclarative |
| **Material 3** | Design System moderne |
| **Room 2.7.0** | Base de données locale |
| **ViewModel + StateFlow** | Gestion d'état (architecture MVVM) |
| **Coroutines** | Programmation asynchrone |
| **AGP 8.10.1** | Android Gradle Plugin |

---

## 📋 Prérequis

- **Android Studio** (version récente — Meerkat ou plus)
- **JDK 21** (inclus dans Android Studio)
- **SDK Android** : API 24 minimum (Android 7.0 Nougat)
- **SDK compilé** : API 36

---

## 🚀 Installation

### 1. Cloner le projet

```bash
git clone https://github.com/Boignon/hydratation_app.git
cd hydratation_app
