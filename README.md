# SuperMarioMLP

This project shows that MLP (Multi layer perceptrons) can be used in videogames to create characters with advanced AI capabilities.The main advantage of this aproach is the ability that neural networks have to simulate the natural learnig process.

<img src="https://github.com/lopeLH/SuperMarioRNA/blob/master/Executable%20JAR/resources/mainImage.png"  width=500 height=250 />

In this example, Mario is controlled by the human player for a while, as Mario plays, information about his decisions is 
stored, and then the neural network is trained against this data. When the training is done Mario leaves the scene and Luigi starts
to play commanded by the neural network. Alternatively Mario can be controlled by a classical AI (if-else logic) so the user doesn't have 
to play the game and the neural network learns from this classical AI.

This architecture makes posible creating games where AI characters learn from the human user, evolving the game as the human 
player adquires new skills.

NetBeans project is provided in adition to the executable JAR. The following libraries are used:

- Encog http://www.heatonresearch.com/encog
- Slick2D http://slick.ninjacave.com/ (http://slick.ninjacave.com/wiki/index.php?title=Setting_up_Slick2D_with_NetBeansIDE)


