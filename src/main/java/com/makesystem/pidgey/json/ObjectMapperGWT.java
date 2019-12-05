/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.json;

import java.io.Serializable;

/**
 * <h2>Quick start</h2>
 * <p>
 * Add
 * <code>&lt;inherits name="com.github.nmorel.gwtjackson.GwtJackson" /&gt;</code>
 * to your module descriptor XML file.</p>
 * <p>
 * Then just create an interface extending <code>ObjectReader</code>,
 * <code>ObjectWriter</code> or <code>ObjectMapper</code> if you want to read
 * JSON, write an object to JSON or both.</p>
 * <p>
 * Here's an example without annotation :</p>
 * <div class="highlight highlight-source-java"><pre><span class="pl-k">public</span> <span class="pl-k">class</span> <span class="pl-en">TestEntryPoint</span> <span class="pl-k">implements</span> <span class="pl-e">EntryPoint</span> {
 *
 * <span class="pl-k">public</span> <span class="pl-k">static</span> <span class="pl-k">interface</span> <span class="pl-en">PersonMapper</span> <span class="pl-k">extends</span> <span class="pl-e">ObjectMapper&lt;<span class="pl-smi">Person</span>&gt;</span> {}
 *
 * <span class="pl-k">public</span> <span class="pl-k">static</span> <span class="pl-k">class</span> <span class="pl-en">Person</span> {
 *
 * <span class="pl-k">private</span> <span class="pl-smi">String</span> firstName;
 * <span class="pl-k">private</span> <span class="pl-smi">String</span> lastName;
 *
 * <span class="pl-k">public</span> <span class="pl-smi">String</span> <span class="pl-en">getFirstName</span>() {
 * <span class="pl-k">return</span> firstName;
 * }
 *
 * <span class="pl-k">public</span> <span class="pl-k">void</span> <span class="pl-en">setFirstName</span>(<span class="pl-smi">String</span> <span class="pl-v">firstName</span>) {
 * <span class="pl-c1">this</span><span class="pl-k">.</span>firstName <span class="pl-k">=</span> firstName;
 * }
 *
 * <span class="pl-k">public</span> <span class="pl-smi">String</span> <span class="pl-en">getLastName</span>() {
 * <span class="pl-k">return</span> lastName;
 * }
 *
 * <span class="pl-k">public</span> <span class="pl-k">void</span> <span class="pl-en">setLastName</span>(<span class="pl-smi">String</span> <span class="pl-v">lastName</span>) {
 * <span class="pl-c1">this</span><span class="pl-k">.</span>lastName <span class="pl-k">=</span> lastName;
 * }
 * }
 *
 * <span class="pl-k">@Override</span>
 * <span class="pl-k">public</span> <span class="pl-k">void</span> <span class="pl-en">onModuleLoad</span>() {
 * <span class="pl-smi">PersonMapper</span> mapper <span class="pl-k">=</span> <span class="pl-c1">GWT</span><span class="pl-k">.</span>create( <span class="pl-smi">PersonMapper</span><span class="pl-k">.</span>class );
 *
 * <span class="pl-smi">String</span> json <span class="pl-k">=</span> mapper<span class="pl-k">.</span>write( <span class="pl-k">new</span> <span class="pl-smi">Person</span>( <span class="pl-s"><span class="pl-pds">"</span>John<span class="pl-pds">"</span></span>, <span class="pl-s"><span class="pl-pds">"</span>Doe<span class="pl-pds">"</span></span> ) );
 * <span class="pl-c1">GWT</span><span class="pl-k">.</span>log( json ); <span class="pl-c"><span class="pl-c">//</span> &gt; {"firstName":"John","lastName":"Doe"}</span>
 *
 * <span class="pl-smi">Person</span> person <span class="pl-k">=</span> mapper<span class="pl-k">.</span>read( json );
 * <span class="pl-c1">GWT</span><span class="pl-k">.</span>log( person<span class="pl-k">.</span>getFirstName() <span class="pl-k">+</span> <span class="pl-s"><span class="pl-pds">"</span> <span class="pl-pds">"</span></span> <span class="pl-k">+</span> person<span class="pl-k">.</span>getLastName() ); <span class="pl-c"><span class="pl-c">//</span> &gt; John Doe</span>
 * }
 * }</pre></div>
 * <p>
 * And if you want to make your class immutable for example, you can add some
 * Jackson annotations :</p>
 * <div class="highlight highlight-source-java"><pre><span class="pl-k">public</span> <span class="pl-k">class</span> <span class="pl-en">TestEntryPoint</span> <span class="pl-k">implements</span> <span class="pl-e">EntryPoint</span> {
 *
 * <span class="pl-k">public</span> <span class="pl-k">static</span> <span class="pl-k">interface</span> <span class="pl-en">PersonMapper</span> <span class="pl-k">extends</span> <span class="pl-e">ObjectMapper&lt;<span class="pl-smi">Person</span>&gt;</span> {}
 *
 * <span class="pl-k">public</span> <span class="pl-k">static</span> <span class="pl-k">class</span> <span class="pl-en">Person</span> {
 *
 * <span class="pl-k">private</span> <span class="pl-k">final</span> <span class="pl-smi">String</span> firstName;
 * <span class="pl-k">private</span> <span class="pl-k">final</span> <span class="pl-smi">String</span> lastName;
 *
 * <span class="pl-k">@JsonCreator</span>
 * <span class="pl-k">public</span> <span class="pl-en">Person</span>( <span class="pl-k">@JsonProperty</span>( <span class="pl-s"><span class="pl-pds">"</span>firstName<span class="pl-pds">"</span></span> ) <span class="pl-smi">String</span> <span class="pl-v">firstName</span>,
 * <span class="pl-k">@JsonProperty</span>( <span class="pl-s"><span class="pl-pds">"</span>lastName<span class="pl-pds">"</span></span> ) <span class="pl-smi">String</span> <span class="pl-v">lastName</span> ) {
 * <span class="pl-c1">this</span><span class="pl-k">.</span>firstName <span class="pl-k">=</span> firstName;
 * <span class="pl-c1">this</span><span class="pl-k">.</span>lastName <span class="pl-k">=</span> lastName;
 * }
 *
 * <span class="pl-k">public</span> <span class="pl-smi">String</span> <span class="pl-en">getFirstName</span>() {
 * <span class="pl-k">return</span> firstName;
 * }
 *
 * <span class="pl-k">public</span> <span class="pl-smi">String</span> <span class="pl-en">getLastName</span>() {
 * <span class="pl-k">return</span> lastName;
 * }
 * }
 *
 * <span class="pl-k">@Override</span>
 * <span class="pl-k">public</span> <span class="pl-k">void</span> <span class="pl-en">onModuleLoad</span>() {
 * <span class="pl-smi">PersonMapper</span> mapper <span class="pl-k">=</span> <span class="pl-c1">GWT</span><span class="pl-k">.</span>create( <span class="pl-smi">PersonMapper</span><span class="pl-k">.</span>class );
 *
 * <span class="pl-smi">String</span> json <span class="pl-k">=</span> mapper<span class="pl-k">.</span>write( <span class="pl-k">new</span> <span class="pl-smi">Person</span>( <span class="pl-s"><span class="pl-pds">"</span>John<span class="pl-pds">"</span></span>, <span class="pl-s"><span class="pl-pds">"</span>Doe<span class="pl-pds">"</span></span> ) );
 * <span class="pl-c1">GWT</span><span class="pl-k">.</span>log( json ); <span class="pl-c"><span class="pl-c">//</span> &gt; {"firstName":"John","lastName":"Doe"}</span>
 *
 * <span class="pl-smi">Person</span> person <span class="pl-k">=</span> mapper<span class="pl-k">.</span>read( json );
 * <span class="pl-c1">GWT</span><span class="pl-k">.</span>log( person<span class="pl-k">.</span>getFirstName() <span class="pl-k">+</span> <span class="pl-s"><span class="pl-pds">"</span> <span class="pl-pds">"</span></span> <span class="pl-k">+</span> person<span class="pl-k">.</span>getLastName() ); <span class="pl-c"><span class="pl-c">//</span> &gt; John Doe</span>
 * }
 * }</pre></div>
 *
 * @author Richeli Vargas
 */
public final class ObjectMapperGWT implements Serializable {

    private static final long serialVersionUID = 3557269063233153802L;

    private ObjectMapperGWT() {
    }

}
