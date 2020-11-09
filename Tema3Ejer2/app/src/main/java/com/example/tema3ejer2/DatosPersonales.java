package com.example.tema3ejer2;

public class DatosPersonales {
    /**
     * Construye un nuevo objeto de datos personales
     * a partir de una sola cadena de caracteres en la que los datos
     * se separan por '\n'
     * @param info Una cadena de caracteres con los datos separados por \n
     */
    public DatosPersonales(String info)
    {
        String[] datos = info.split( "\n" );
        int numDatos = datos.length;

        if ( numDatos > 0 ) {
            this.setNombre( datos[ 0 ] );
        }

        if ( numDatos > 1 ) {
            this.setDireccion( datos[ 1 ] );
        }

        if ( numDatos > 2 ) {
            this.setEmail( datos[ 2 ] );
        }

        if ( numDatos > 3 ) {
            this.setTelf( datos[ 3 ] );
        }

        if ( numDatos > 4 ) {
            this.setApellido(datos[ 4 ] );
        }

        return;
    }

    /**
     * Construye un nuevo objeto de datos personales
     * a partir de los datos individuales
     * @param n El nombre, como texto.
     * @param a La dirección, como texto.
     * @param e El e.mail, como texto.
     */
    public DatosPersonales(String n, String a, String e, String t, String s) {
        this.setNombre( n );
        this.setDireccion( a );
        this.setEmail( e );
        this.setTelf( t );
        this.setApellido( s );
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    final public String getNombre() {
        return nombre;
    }

    final public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    final public String getEmail() {
        return email;
    }

    final public void setEmail(String email) {
        this.email = email;
    }

    final public String getDireccion() {
        return direccion;
    }

    final public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString()
    {
        StringBuilder toret = new StringBuilder();

        toret.append( this.getNombre() );
        toret.append( '\n' );

        toret.append( this.getDireccion() );
        toret.append( '\n' );

        toret.append( this.getEmail() );
        toret.append( '\n' );

        toret.append( this.getTelf() );
        toret.append( '\n' );

        toret.append( this.getApellido() );
        toret.append( '\n' );

        return toret.toString();
    }

    private String nombre;
    private String email;
    private String direccion;
    private String telf;
    private String apellido;
}