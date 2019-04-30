1) DIFERENCIA ENTRE RUNNABLE Y THREAD

    La primera diferencia es que Runnable es una interfaz (que tiene un solo metodo, run()), y Thread 
    es una clase que extiende de Object e implementa Runnable. En si un Thread es la unidad minima
    de ejecucion que puede correr dentro de un proceso. En java un thread es un objeto con la 
    capacidad de correr en forma concurrente el metodo run().
    Para crear un thread tenemos 2 opciones:
    
        a) Extender de la clase Thread: Cuando creamos una clase que extiende de Thread, tenemos que
        sobreescribir el metodo run() que viene de la clase Thread (que a su vez viene de la interfaz).
        Al hacer esto perdemos la posibilidad de poder extender de otra clase. Al hacer esto tal vez vamos
        a heredar a nuestra clase muchos metodos que no vallamos a necesitar, ademas de estar extendiendo
        una clase sin mejorarla, lo que no tiene mucho sentido. Tambien va a generar acoplamiento ya que
        ademas de tener el comportamiento del Thread vamos a tener la tarea que querramos realizar todo
        en la misma clase.
        Cuando se ejecute el metodo start(), este va a ejecutar el run() que hemos redefinido en la misma
        clase.
        b) Implementando Runnable: La clase Thread entre sus constructores, tiene uno que permite recibir
        un objeto que implemente Runnable. Para esto creamos un objeto que implemente la interfaz, y se lo
        pasamos al constructor del Thread. 
        De esta forma tenemos la ejecucion asincrona (el thread) y el comportamiento (la clase 
        que implementa Runnable) por separado, lo que hace que la clase que implementa pueda ser reutilizada,
        no solo entre varios Threads sino tambien puede ejecutarse sin necesidad de un thread, ya sea al finalizar
        la ejecucion del hilo o cambiar la forma en que se ejecuta.
        Esta opcion nos permite que el comportamiento pueda extender de otra clase, ademas de tener la posibilidad
        de compartir una instancia entre varios hilos. 
        Cuando se ejecute el metodo start() del Thread, este va a llamar al metodo run() de la clase que implementa.

2) CICLO DE VIDA DE UN THREAD.

    Un Thread tiene 5 etapas en su ciclo de vida:

    a) NEW: Un Thread esta en este estado cuando es creado a traves del operador 'new'. Se considera que todavia no 
    es un hilo de ejecucion en este punto, ya que no ha comenzado a ejecutar. Todavia no se han asignado recursos al hilo y 
    desde este punto solo podemos ejecutar el metodo start() del hilo.
    
    b) RUNNABLE: Cuando se invoca al metodo start(), el thread entra en este estado, lo que hace que el hilo este disponible
    para ejecutar hasta que el planificador lo seleccione para ponerlo a ejecutar. O sea que en este estado esta elegible
    para ser ejecutado.
    
    c) RUNNING: Cuando el planificador selecciona al hilo para ejecutar y le asigna los recursos, el thread pasa a ejecutando
    durante un tiempo determinado por el sistema operativo. Un hilo desde este estado puede pasar a espera/bloqueado,
    si se le termina el tiempo de ejecucion sin haber terminado de ejecutar el metodo run() o si bien se llama al metodo wait(),
    puede tambien pasar a dormido, si se ejecuta el metodo sleep() o tambien pasar a terminado si ha ejecutado el metodo run() por 
    completo.
    
    d) BLOCKED/WAITING: Un hilo esta en este estado cuando esta temporalmente inactivo. Puede ser que este bloqueado esprando que se
    libere algun recurso, que este siendo ocupado por otro hilo en un momento dado.
    Desde este estado el hilo no pasa directamente a ejecucion sino a listo para ser ejecutado, y esperar que el planificador lo 
    seleccione de nuevo.
    
    e) TIMED WAITING: Un hilo esta en este estado cuando ejecuta el metodo sleep() y esto hace que el hilo quede temporalmente
    dormido por el tiempo especificado, luego pasa a ejecutable.
    
    f) TERMINATED: Un hilo termina o muere cuando ejecuta todo el codigo que tiene en el metodo run(). Tambien puede pasar que 
    ocurra algun error en el sistema y el hilo termina forzosamente. Un hilo muerto ya no consume mas recursos.


3) EXPLIQUE LOS METODOS START, SLEEP, YIELD Y JOIN.

    a) START: Cuando el thread ejecuta este metodo, pasa a estar vivo, o sea que es elegible para ser corrido. Cuando es elegido
    pasa a ejecutar el codigo que se encuantra en el metodo run(), ya sea del mismo thread, si es que extendimos la clase, o del objeto
    que implementa la interfaz Runnable. Si llamamos a run() directamente, va a pasar que el codigo se ejecuta en el contexto del
    mismo hilo de forma sincrona, perdiendo asi la funcionalidad real de un hilo.

    b) SLEEP: Este metodo es usado para dormir un hilo por una cantidad especificada de milisegundos, o milisigundos mas nanosegundos.
    La cantidad de tiempo pasada al metodo no puede ser negativa, sino se genera una IllegalArgumentException. Normalmente el tiempo
    que tarda en despertar el hilo es lo mas cercano a lo que especificamos, pero puede suceder que se tarde un poco mas. Todo 
    depende de la carga que tenga la CPU en ese momento. Cuando el hilo es despertado, va a esperar a que se le asigne tiempo
    de CPU para volver a ejecutar.
    
    c) YIELD: Este metodo hace que el hilo pase a dejarle la CPU a otros hilos, ya sea porque tienen mas prioridad o porque la tarea
    que van a realizar es mas importante. Se desaconseja el uso de este metodo en la documentacion de java.
    
    d) JOIN: Se usa este metodo para indicarle a un hilo que debe esperar que otro hilo pase a estado terminado para poder ejecutar.
    Un hilo indica a que hilo debe esperar ejecutando: hiloAEsperar.join();
