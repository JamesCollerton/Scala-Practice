package org.example.application
package traits

/*
  Traits seem to have been introduced in order to battle some of the deficiencies of interfaces. Mainly that they
  couldn't declare non-static fields or method bodies
 */
object TraitsIntroductoryExampleClass {

  /*
    Note: which version of click gets called first when we mix in VetoableClicks and ObservableClicks? The answer is
    the declaration order determines the order, from right to left.
   */
  def main(args: Array[String]): Unit = {
    val observableButtonSubject = new ObservableButtonSubject("observable button subject")
    val buttonCountObserver = new ButtonCountObserver

    observableButtonSubject addObserver buttonCountObserver

    observableButtonSubject.click()
    observableButtonSubject.click()

    println(s"Button ${observableButtonSubject.name} count ${buttonCountObserver.count}")

    val observableButtonOnFly = new Button("mix in button") with Subject[Button] {
      override def click(): Unit = {
        super.click()
        notifyObservers(this)
      }
    }

    observableButtonOnFly addObserver buttonCountObserver

    observableButtonOnFly.click()
    observableButtonOnFly.click()

    println(s"Button ${observableButtonOnFly.name} count ${buttonCountObserver.count}")

    val observableButtonObservableClick = new ClickableButton("observable click button") with ObservableClick

    val clickCountObserver = new ClickCountObserver

    observableButtonObservableClick addObserver clickCountObserver

    observableButtonObservableClick.click()
    observableButtonObservableClick.click()

    println(s"Button ${observableButtonObservableClick.name} count ${clickCountObserver.count}")
  }

  /*
    Note, we don't need to define method bodies in traits.

    This is the observer trait for the observer design pattern. An observer subscribes to updates from a subject. We mix
    this in for anything where we want to watch something that the subject does.
   */
  trait Observer[State] {
    def receiveUpdate(state: State): Unit
  }

  /*
    This is the subject trait. We mix this in whenever we want to have a selection of things listening to the subject.
    It contains a list of observers, each of which are notified when the state changes.
   */
  trait Subject[State] {
    private var observers: List[Observer[State]] = Nil

    def addObserver(observer: Observer[State]): Unit = {
      observers ::= observer
    }

    def notifyObservers(state: State): Unit = {
      observers.foreach(_.receiveUpdate(state))
    }
  }

  /*
    A simple button class. This allows us to separate things out as much as possible. In later iterations we see that
    in fact we want to separate out the notion of 'clickable' to not just limit it to buttons.
   */
  abstract class Button(val name: String) {
    def click(): Unit = {
      println(s"We have clicked the $name button!")
    }
  }

  /*
    This is a concrete button implementation. It extends the button class, but mixes in the subject trait. This
    tells us that it is a type of button, but has the features of a subject.

    This is an important distinction:
      - Class hierarchies are used to tell us what something IS
      - Traits are used to tell us some properties of the object
   */
  class ObservableButtonSubject(name: String) extends Button(name: String) with Subject[Button] {
    override def click(): Unit = {
      super.click()
      notifyObservers(this)
    }
  }

  /*
    This is the observer for the button. It extends the observer trait. Notice how observer is a trait not a class,
    as a trait tells us about the features of the class, but does not define it.
   */
  class ButtonCountObserver extends Observer[Button] {
    var count = 0
    def receiveUpdate(state: Button): Unit = count += 1
  }

  /*
    This is a trait used to define anything that is clickable, buttons are included. Notice how the method isn't
    defined, which is possible in traits. When we implement the trait we then need to give the method definition.
   */
  trait Clickable {
    def click(): Unit
  }

  /*
    Here we have a clickable button, which now overrides the click method in the clickable trait.
   */
  class ClickableButton(val name: String) extends Clickable {
    override def click(): Unit = {
      println(s"We have clicked the $name button!")
    }
  }

  /*
    Now we have an observable click, which needs the abstract keyword as we have no method definition for click in
    the clickable trait
   */
  trait ObservableClick extends Clickable with Subject[Clickable] {
    abstract override def click(): Unit = {
      super.click()
      notifyObservers(this)
    }
  }

  /*
    An observer class for clicks
   */
  class ClickCountObserver extends Observer[Clickable] {
    var count = 0
    def receiveUpdate(state: Clickable): Unit = count += 1
  }
}
