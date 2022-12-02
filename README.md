# Learning Netlogo Extension

### Learning Setup Primitives
The **setup** primitives must be executed during the set up of your simulation. In NetLogo this is often done in a `setup` procedure.

It is **extremely recommended** to execute a `clear-all` in the setup procedure. If do not execute it, things will get duplicated and the extension may not work.

Then in the `setup` procedure you must execute an `ask` to the breed you want to be the learners. Inside this `ask` you can run the following primitives:

---

#### `qlearningextension:state-def ["var1" "varN"]`

Used to define the **state representation** of your learner agent.
This primitive receives a list containing variable names that the agent that did the `ask` owns. **Before running any of the primitives below you must first run this primitive**.

---

#### `qlearningextension:state-def ["var1" "varN"] reporter`

Acts exactly the previous primitive, except that it admits an additional `reporter` argument. This primitive is useful when you need to add values to the state definition that are not agent variables.

The `reporter` argument must be a reporter that returns a `string`.  Every time the extension is about to generate a state, this reporter will be called and its return will be added to the state definition of that state.

---

#### `(qlearningextension:action [action1] [action2] [actionN])`
Used to define what **actions** the learner agent can perform.

The primitive receives as argument(s) the action(s) that the agent can perform. You can pass how many actions you want, **but they must be procedures not reporters**.  

Please notice (and do not forget to type) the parentheses encapsulating the primitive call and the brackets surrounding each action.

---

#### `learningextension:reward [rewardFunc]`

Used to define a **reporter** that will return a number with the **reward** for the **current state**.

---

#### `qlearningextension:end-episode [isEndState] resetEpisode`

This primitive should be used in **episodic** learning to reset the problem to its initial state by the end of an episode.

The `isEndState` argument must be a `reporter` that returns a boolean value to indicate whether the current state characterizes the end of an episode. The `resetEpisode` argument must be a `procedure` that resets the agent/environment to its initial state. This `resetEpisode` procedure is called automatically by the extension when `isEndState` returns `true`.

---

#### `qlearningextension:action-selection "policy" []`

Used to define the action selection policy.

The following two selection policies are provided:
- `random-normal`: selects an action at random according to the percentage passed as argument. For example, `qlearningextension:action-selection "random-normal" [0.8]` specifies that 80% of the actions will be selected at random;

- `e-greedy`: also selects an action according to the percentage passed as the first argument, but such percentage is decreased over time according to the second argument. For example, `qlearningextension:action-selection "e-greedy" [0.8 0.99995]` specifies that 80% of the actions will be selected at random, but after each episode this percentage is updated and its new value corresponds to the current value multiplied by the decrease rate.

In both cases the numeric arguments must be between 0 and 1.

---
#### `qlearningextension:action-selection-egreedy epsilonValue "type" decreaseValue`

Used to define the e-greedy action selection policy.

The following two selection types are provided:
- `rate`:  selects an action according to the percentage passed as the first argument, but such percentage is decreased over time according to the third argument. For example, `qlearningextension:action-selection-egreedy 0.8 "rate" 0.99995` specifies that 80% of the actions will be selected at random, but after each episode this percentage is updated and its new value corresponds to the current value multiplied by the decrease rate.

- `value`: selects an action according to the percentage passed as the first argument, but such percentage is changed to the third argument, which can be a report that updates the next epsilon value in the simulation, using its own decay rule. For example, `qlearningextension:action-selection-egreedy 0.8 "value" reportDecrease` specifies that reportDecrease will set the next epsilon value.

In both cases the numeric arguments must be between 0 and 1.

---
#### `qlearningextension:action-selection-random value`

Used to define the random normal action selection policy.

This policy selects an action at random according to the percentage passed as argument. For example, `qlearningextension:action-selection-random 0.8` specifies that 80% of the actions will be selected at random;

The numeric arguments must be between 0 and 1.

---
#### `qlearningextension:learning-rate learningRate`

Used to specify the learning rate. The `learningRate` argument must be a numeric value between 0 and 1.

---

#### `qlearningextension:discount-factor discountFactor`

Used to specify the discount factor. The  `discountFactor`argument must be a numeric value between 0 and 1.

---

### Q-Learning Execution Primitives

After setting up the extension, you must modify your simulation execution procedure (often called `go`) to activate the Q-Learning algorithm.

The following primitives are available to activate the Q-Learning algorithm. These must be executed within an `ask` block, to ask your learner agents to learn.

---

#### `qlearningextension:learning <boolean-value>`

This primitives performs a Q-Learning step, which consist of the following sequence of steps:

1. select an action in the current state (according the selection policy) and performs it.
2. compute the reward.
3. update the Q-table.
4. check whether the new state is a final state, and if so, resets the agent/environment.

The <boolean-value> argument indicates whether to run the learning step in debug mode, as detailed below.

To help you in debugging your simulation, you can execute the learning primitive `qlearningextension:learning true`. Calling the primitive this way will make the extension print in the NetLogo console the following values: the old state and action, the old Q-list (the Q-table values of the old state), the new state, the observed reward, the expected reward of the new state and the new Q-list. Finally, if the action selection method uses epsilon, it will be printed with the other information.

If you don't want to print the Q-Table on every learning step, just change the primitive's value to `qlearningextension:learning false`.

Another way to debugging your simulations calling the `qlearningextension:get-qtable` primitive, this will return a string with the current Q-table.

---

#### `qlearningextension:learn`

Is responsible for learning the agents and updating the Q-Table to assist in the decision making of new actions. As well as the learning method, there is the possibility to use it in the following way `(qlearningextension:learn true)` for debugging

---

#### `qlearningextension:act`

It is responsible for making the action decision, as well as executing the action.
