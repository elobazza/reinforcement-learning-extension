# Reinforcement Learning Netlogo Extension

### Learning Setup Primitives
The **setup** primitives must be executed during the set up of your simulation. In NetLogo this is often done in a `setup` procedure.

It is **extremely recommended** to execute a `clear-all` in the setup procedure. If do not execute it, things will get duplicated and the extension may not work.

Then in the `setup` procedure you must execute an `ask` to the breed you want to be the learners. Inside this `ask` you can run the following primitives:

---

#### `learningextension:state-def ["var1" "varN"]`

Used to define the **state representation** of your learner agent.
This primitive receives a list containing variable names that the agent that did the `ask` owns. **Before running any of the primitives below you must first run this primitive**.

---

#### `(learningextension:action [action1] [action2] [actionN])`
Used to define what **actions** the learner agent can perform.

The primitive receives as argument(s) the action(s) that the agent can perform. You can pass how many actions you want, **but they must be procedures not reporters**.  

Please notice (and do not forget to type) the parentheses encapsulating the primitive call and the brackets surrounding each action.

---

#### `learningextension:reward [rewardFunc]`

Used to define a **reporter** that will return a number with the **reward** for the **current state**.

---

#### `learningextension:end-episode [isEndState] resetEpisode`

This primitive should be used in **episodic** learning to reset the problem to its initial state by the end of an episode.

The `isEndState` argument must be a `reporter` that returns a boolean value to indicate whether the current state characterizes the end of an episode. The `resetEpisode` argument must be a `procedure` that resets the agent/environment to its initial state. This `resetEpisode` procedure is called automatically by the extension when `isEndState` returns `true`.

---

#### `learningextension:action-selection "policy" []`

Used to define the action selection policy.

The following two selection policies are provided:
- `random-normal`: selects an action at random according to the percentage passed as argument. For example, `qlearningextension:action-selection "random-normal" [0.8]` specifies that 80% of the actions will be selected at random;

- `e-greedy`: also selects an action according to the percentage passed as the first argument, but such percentage is decreased over time according to the second argument. For example, `qlearningextension:action-selection "e-greedy" [0.8 0.99995]` specifies that 80% of the actions will be selected at random, but after each episode this percentage is updated and its new value corresponds to the current value multiplied by the decrease rate.

In both cases the numeric arguments must be between 0 and 1.

---
#### `learningextension:action-selection-egreedy epsilonValue "type" decreaseValue`

Used to define the e-greedy action selection policy.

The following two selection types are provided:
- `rate`:  selects an action according to the percentage passed as the first argument, but such percentage is decreased over time according to the third argument. For example, `qlearningextension:action-selection-egreedy 0.8 "rate" 0.99995` specifies that 80% of the actions will be selected at random, but after each episode this percentage is updated and its new value corresponds to the current value multiplied by the decrease rate.

- `value`: selects an action according to the percentage passed as the first argument, but such percentage is changed to the third argument, which can be a report that updates the next epsilon value in the simulation, using its own decay rule. For example, `qlearningextension:action-selection-egreedy 0.8 "value" reportDecrease` specifies that reportDecrease will set the next epsilon value.

In both cases the numeric arguments must be between 0 and 1.

---
#### `learningextension:action-selection-random value`

Used to define the random normal action selection policy.

This policy selects an action at random according to the percentage passed as argument. For example, `qlearningextension:action-selection-random 0.8` specifies that 80% of the actions will be selected at random;

The numeric arguments must be between 0 and 1.

---
#### `learningextension:learning-rate learningRate`

Used to specify the learning rate. The `learningRate` argument must be a numeric value between 0 and 1.

---

#### `learningextension:discount-factor discountFactor`

Used to specify the discount factor. The `discountFactor`argument must be a numeric value between 0 and 1.

---
#### `learningextension:lambda lambda`

Used to specify the lambda value. The `lambda` argument must be a numeric value between 0 and 1.

---
#### `learningextension:define-algorithm "nameAlgorithm"`

Used to specify the algorithm to be used. The `nameAlgorithm` argument must be a string value with the algorithm name.
Possible values: "qlearning", "sarsa-lambda" and "actor-critic"

---
#### `learningextension:setup`

Used to instantiate BURLAP objects with pre-established information. Must be used at the end of all extension setup primitives.

---
### Learning Execution Primitives

After setting up the extension, you must modify your simulation execution procedure (often called `go`) to activate the Learning algorithm.

The following primitives are available to activate the Learning algorithm. These must be executed within an `ask` block, to ask your learner agents to learn.

---

#### `learningextension:learning`

This primitives performs a Learning step of the algorithm that was selected.

---
### How to embed the Learning Extension

in the NetLogo directory, in the `\app\extensions` subdirectory, you must create a directory called `learningextension` and add the three `.jar` files from the `jars` folder of this repository.

